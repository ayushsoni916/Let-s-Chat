package com.cake.letschat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.se.omapi.Session;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class photoName extends AppCompatActivity {
    ImageView btn , btnPlan , photo;
    Animation btnAnim;
    Uri imageURI=null;
    String firstName , lastName , phoneNumber;

    EditText fName , lName;

    FirebaseDatabase data;
    DatabaseReference ref;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_name);
        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        btn = findViewById(R.id.imageView7);
        btnPlan = findViewById(R.id.imageView8);
        photo = findViewById(R.id.imageView9);
        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);

        btnAnim = AnimationUtils.loadAnimation(this,R.anim.button_anim);
//        phone=Intent.getIntent()
        phoneNumber = getIntent().getStringExtra("number").trim();
        data =FirebaseDatabase.getInstance();
//        firstName = fName.getText().toString();
//        lastName = lName.getText().toString();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.startAnimation(btnAnim);
                btnPlan.startAnimation(btnAnim);
                firstName = fName.getText().toString();
                lastName = lName.getText().toString();
                if(!(firstName.isEmpty() && lastName.isEmpty())){
                session(firstName , lastName ,phoneNumber , String.valueOf(imageURI));
                ref =data.getReference().child("users/"+phoneNumber);
                ref.child("firstName").setValue(firstName);
                ref.child("lastName").setValue(lastName);
                ref.child("phoneNumber").setValue(phoneNumber);

            }
                else if(firstName.isEmpty()){
                    fName.setError("Enter First Name");
                }
                else if (lastName.isEmpty()){
                    lName.setError("Enter Last Name");
                }
                else{
                    fName.setError("Enter First Name");
                    lName.setError("Enter Last Name");
                }
            }

        });
        btnPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.startAnimation(btnAnim);
                btnPlan.startAnimation(btnAnim);
                firstName = fName.getText().toString();
                lastName = lName.getText().toString();
                if(!(firstName.isEmpty() && lastName.isEmpty())){
                    session(firstName , lastName ,phoneNumber , String.valueOf(imageURI));
                    ref =data.getReference().child("users/"+phoneNumber);
                    ref.child("firstName").setValue(firstName);
                    ref.child("lastName").setValue(lastName);
                    ref.child("phoneNumber").setValue(phoneNumber);
                }
                else if(firstName.isEmpty()){
                    fName.setError("Enter First Name");
                }
                else if (lastName.isEmpty()){
                    lName.setError("Enter Last Name");
                }
                else{
                    fName.setError("Enter First Name");
                    lName.setError("Enter Last Name");
                }
                }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo.startAnimation(btnAnim);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImagePicker.with(photoName.this)
                                .crop()	    			//Crop image(Optional), Check Customization for more option
                                .compress(200)			//Final image size will be less than 1 MB(Optional)
                                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                                .start();


                    }

                },70);
            }
        });
    }
    //create a session
    public void session(String firstName , String Lastname , String phone , String profile){
        sessionManagerForLogIn sessionManager = new sessionManagerForLogIn(getApplicationContext());
        sessionManager.createLoginSession(firstName, Lastname, phone, profile);
        Intent i = new Intent(photoName.this , dashboard.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageURI = data.getData();
        if (imageURI!= null){
            photo.setImageURI(imageURI);
            SimpleDateFormat format
                     = new SimpleDateFormat("yyyy_MM_dd_HH_mm__ss", Locale.CANADA);
            java.util.Date now =new Date();
            String fileName = format.format(now);
            storageReference = FirebaseStorage.getInstance().getReference("Images").child("userProfilePic/"+fileName);
            storageReference.putFile(imageURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Uri> task) {
                                    ref = FirebaseDatabase.getInstance().getReference().child("users").child(phoneNumber);
                                    ref.child("profilePic").setValue(task.getResult().toString());

                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(getApplicationContext(),"fail to upload image",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}