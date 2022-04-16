package com.cake.letschat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class message extends AppCompatActivity {
    private LinearLayout mBottomSheetLayout;
    private BottomSheetBehavior sheetBehavior;
    ImageView profile;
    TextView name ;
    String nameStr,ImgStr;
    ImageView song;
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

        name = findViewById(R.id.textView6);
        profile = findViewById(R.id.imageView27);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            name.setText(extras.getString("name"));
            profile.setImageResource(extras.getInt("img"));
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
         }
}