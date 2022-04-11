package com.cake.letschat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 1500;

    ImageView logoPlan,logo ;
    TextView  logoNameOne , logoNameTwo;
    Animation topAnim , bottomAnim , planAnim;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }
        logoNameOne = findViewById(R.id.logonameone);
        logoNameTwo = findViewById(R.id.logonametwo);
        logoPlan = findViewById(R.id.logoplan);
        logo =findViewById(R.id.imageView);

        topAnim = AnimationUtils.loadAnimation(MainActivity.this,R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bottom_anim);
        planAnim = AnimationUtils.loadAnimation(MainActivity.this,R.anim.plan_anim);
        logoNameOne.setAnimation(topAnim);
        logoNameTwo.setAnimation(bottomAnim);

        logoPlan.setAnimation(planAnim);
        sessionManagerForLogIn sessionManager = new sessionManagerForLogIn(getApplicationContext());
        HashMap<String ,String> userDetails = sessionManager.getUserDataFromSession();

        String firstName = userDetails.get(sessionManager.KEY_FIRSTNAME);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firstName == null){
                Intent intent = new Intent(MainActivity.this, phoneVerification.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(logoPlan, "plan");
                pairs[1] = new Pair<View, String>(logo, "logo");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent, options.toBundle());
                finish();
                }
                else{
                    Intent i =new Intent(getApplicationContext(),dashboard.class);
                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(logoPlan, "plan");
//                    pairs[1] = new Pair<View, String>(logo, "logo");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                    startActivity(i, options.toBundle());
                    finish();
                }
                //startActivity(new Intent(MainActivity.this , Login.class));

            }
        },SPLASH_SCREEN);

    }
}