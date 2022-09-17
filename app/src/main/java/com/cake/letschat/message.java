package com.cake.letschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.cake.letschat.Adapters.messageAdapter;
import com.cake.letschat.Adapters.songSheetAdapter;
import com.cake.letschat.models.messageModel;
import com.cake.letschat.models.songSheetModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class message extends AppCompatActivity implements songSheetAdapter.OnSongClickListener {
    private LinearLayout mBottomSheetLayout;
    private BottomSheetBehavior sheetBehavior;
    ImageView profile;
    TextView name ;
    String nameStr,ImgStr,receiver,Sender,text="";
    ImageView song,msgSend;
    EditText msg;
    messageAdapter adapter;
    songSheetAdapter sheetAdapter;
    RecyclerView rcv , songRcv ;
    LinearLayoutManager manager,songManager;

    FirebaseDatabase data;
    DatabaseReference ref;

    ImageView songImg ;
    TextView songName;
    LinearLayout songLayout;
    String size;



    public String songUrl;

    //**********************************media Player***********
    private ImageView imagePlayPause;
    private SeekBar playSeekBar;
    private ProgressBar progressBar;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    //************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.statusBlue));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        song = findViewById(R.id.imageView28);
        mBottomSheetLayout = findViewById(R.id.bottomSheet);
        sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);

        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        msg = findViewById(R.id.msg);
        msgSend = findViewById(R.id.send);

        name = findViewById(R.id.textView6);
        profile = findViewById(R.id.imageView27);

        data =FirebaseDatabase.getInstance();

        rcv = findViewById(R.id.rcvText);
        songRcv = findViewById(R.id.songRcv);

        songImg = findViewById(R.id.imageView32);
        songName = findViewById(R.id.textView11);
        songLayout = findViewById(R.id.linearLayout3);


        imagePlayPause = findViewById(R.id.imageView34);
        playSeekBar = findViewById(R.id.seekBar3);
        progressBar = findViewById(R.id.progress_bar);

        mediaPlayer = new MediaPlayer();

        playSeekBar.setMax(100);
        progressBar.setMax(100);

        imagePlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                        handler.removeCallbacks(updater);
                        mediaPlayer.pause();
                        imagePlayPause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                }
                else {
                        mediaPlayer.start();
                        imagePlayPause.setImageResource(R.drawable.ic_baseline_pause_24);
                        updateSeekBar();
                }
            }
        });
        prepareMediaPlayer();

        playSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SeekBar seekBar =(SeekBar) view;
                int playPosition = (mediaPlayer.getDuration()/100)* seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);

                return false;
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                playSeekBar.setSecondaryProgress(i);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playSeekBar.setProgress(0);
                progressBar.setProgress(0);
                imagePlayPause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                mediaPlayer.reset();
                prepareMediaPlayer();
            }
        });



//        ********************************************************************
        //getting data from session****************************


        sessionManagerForLogIn sessionManager = new sessionManagerForLogIn(getApplicationContext());
        HashMap<String ,String> userDetails = sessionManager.getUserDataFromSession();

        Sender = userDetails.get(sessionManager.KEY_PHONE);
//        Toast.makeText(this, Sender, Toast.LENGTH_SHORT).show();
//*********************************************************************************







        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            name.setText(extras.getString("name"));
            receiver = extras.getString("phoneNumber");
            String pic = extras.getString("img");
            Glide.with(profile.getContext()).load(pic).thumbnail(0.1f)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(profile);
        }
        song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){

                    sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

            }
        });


        msgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = msg.getText().toString();
                if(!text.equals("")){
                    SimpleDateFormat formater = new SimpleDateFormat("yyyy_MM_dd_HH_mm__ss", Locale.CANADA);
                    Date now =new Date();
                    String fileName = formater.format(now);


                    ref =data.getReference().child("users/"+Sender).child("chats").child(receiver).child(fileName);
                    ref.child("message").setValue(text);
                    ref.child("yourStatus").setValue("sender");

                    ref =data.getReference().child("users/"+receiver).child("chats").child(Sender).child(fileName);
                    ref.child("message").setValue(text);
                    ref.child("yourStatus").setValue("receiver");
                    msg.setText(null);
                    ref=data.getReference().child("users/"+receiver).child("chats").child(Sender);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                           long size = snapshot.getChildrenCount();
//                            Toast.makeText(message.this, size, Toast.LENGTH_SHORT).show();
                            rcv.smoothScrollToPosition((int) (size-1));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
//



                }
            }
        });

        manager =new LinearLayoutManager(getApplicationContext());
        songManager = new LinearLayoutManager(getApplicationContext());
        manager.setStackFromEnd(true);
        rcv.setLayoutManager(manager);
        songRcv.setLayoutManager(songManager);

        FirebaseRecyclerOptions<messageModel> options =
                new FirebaseRecyclerOptions.Builder<messageModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users/"+Sender).child("chats").child(receiver), messageModel.class)
                        .build();

        adapter = new messageAdapter(options , getApplicationContext());
        rcv.setAdapter(adapter);


        FirebaseRecyclerOptions<songSheetModel> option =
                new FirebaseRecyclerOptions.Builder<songSheetModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("music"), songSheetModel.class)
                        .build();

        sheetAdapter = new songSheetAdapter(option , getApplicationContext(),this);
        songRcv.setAdapter(sheetAdapter);
         }

    @Override
    protected void onStart() {
        adapter.startListening();
        sheetAdapter.startListening();
        super.onStart();

    }

    @Override
    public void onSongClick(String[] data) {

//        Toast.makeText(this, data[0], Toast.LENGTH_SHORT).show();
        songUrl = data[2];

        songLayout.setVisibility(View.VISIBLE);
        songName.setText(data[1]);
        Glide.with(getApplicationContext()).load(data[0]).thumbnail(0.01f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(songImg);
//        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        playSeekBar.setProgress(0);
        progressBar.setProgress(0);
//        imagePlayPause.setImageResource(R.drawable.ic_baseline_pause_24);
//        mediaPlayer.reset();
         prepareMediaPlayer();
//         mediaPlayer.start();


    }
    //*************songs--method**************************************************************************


    private void prepareMediaPlayer(){
        try {
//            mediaPlayer.release();
//            mediaPlayer.reset();

//            mediaPlayer.setDataSource(songUrl);
//                mediaPlayer.setDataSource("https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3");
                mediaPlayer.setDataSource("https://infinityandroid.com/music/good_times.mp3");
            mediaPlayer.prepareAsync();



//            textTotalDuration.setText(milliSecondToTimer(mediaPlayer.getDuration()));
        }catch (Exception exception){
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
//            textCurrentTime.setText(milliSecondToTimer(currentDuration));
        }
    };
    
    

    private  void updateSeekBar(){
        if(mediaPlayer.isPlaying()){
            playSeekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration())*100));
            progressBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration())*100));
            handler.postDelayed(updater , 1000);
        }
    }
    private  String milliSecondToTimer(long milliSeconds){
        String timerString = "";
        String secondString;

        int hours = (int)(milliSeconds/(1000*60*60));
        int minutes = (int) (milliSeconds%(1000*60*60))/(1000*60);
        int seconds=(int)((milliSeconds % (1000*60*60))%(1000*60)/1000);

        if(hours > 0){
            timerString = hours+":";
        }
        if(seconds < 10){
            secondString= "0"+seconds;
        }
        else {
            secondString=""+seconds;
        }
        timerString = timerString + minutes + ":" + secondString;
        return  timerString;
    }
}