package com.cake.letschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.jetbrains.annotations.NotNull;

import pl.droidsonroids.gif.GifImageView;

public class phoneVerification extends AppCompatActivity {
    ImageView btn,btnPlan;

    EditText phoneNo;
    Animation buttonAnim;

    GifImageView  loading;
    String number;

    private FirebaseDatabase data;
    private DatabaseReference ref;

    boolean existinguser = false;
    CountryCodePicker ccp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        buttonAnim = AnimationUtils.loadAnimation(this,R.anim.button_anim);

        btn = findViewById(R.id.btn);
        btnPlan = findViewById(R.id.btnPlan);

        data = FirebaseDatabase.getInstance();
        ref = data.getReference().child("users");

        loading = findViewById(R.id.loading);
        loading.setVisibility(View.INVISIBLE);

        phoneNo=findViewById(R.id.editTextPhone);

        ccp = (CountryCodePicker) findViewById(R.id.countryCodePicker);
//        ccp.registerPhoneNumberTextView(phoneNo);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.startAnimation(buttonAnim);
                btnPlan.startAnimation(buttonAnim);
                number = phoneNo.getText().toString().trim();
                String couCode = ccp.getSelectedCountryCodeWithPlus();
//                Toast.makeText(getApplicationContext() , couCode+number,Toast.LENGTH_SHORT).show();

                if (number.length()==10){
                    loading.setVisibility(View.VISIBLE);
                    ref.child(couCode+number).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if(snapshot.hasChild("firstName")){
//                                Toast toast = new Toast(this,"dsv",Toast.LENGTH_SHORT).show();
//                                Toast.makeText(this,"exist user",Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(), "exist user",
//                                        Toast.LENGTH_LONG).show();
                            }
                            else {

                                FirebaseDatabase.getInstance().getReference().child("users").child(couCode+number).setValue(couCode+number);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i=new Intent(phoneVerification.this,otp.class);
                            Pair[] pairs = new Pair[2];
                            pairs[0] = new Pair<View, String>(btnPlan, "btnPlan");
                            pairs[1] = new Pair<View, String>(btn, "btn");
                            i.putExtra("number",couCode+number);

                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(phoneVerification.this, pairs);
                            loading.setVisibility(View.INVISIBLE);
                            startActivity(i, options.toBundle());
//                            startActivity(i);
                        }
                    },1000);
//                    phoneNo.setError("");
                }
                else {
                    phoneNo.setError("Invalid phone no.");
                    loading.setVisibility(View.INVISIBLE);
                }
            }
        });
        btnPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.startAnimation(buttonAnim);
                btnPlan.startAnimation(buttonAnim);
                number = phoneNo.getText().toString();
//                data = FirebaseDatabase.getInstance();
//                ref = data.getReference().child("users");
                String couCode = ccp.getSelectedCountryCodeWithPlus();
//                Toast.makeText(getApplicationContext() , couCode+number,Toast.LENGTH_SHORT).show();

                if (number.length()==10){
                    loading.setVisibility(View.VISIBLE);
                    ref.child(couCode+number).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if(snapshot.hasChild("firstName")){
//                                Toast toast = new Toast(this,"dsv",Toast.LENGTH_SHORT).show();
//                                Toast.makeText(this,"exist user",Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(), "exist user",
//                                        Toast.LENGTH_LONG).show();
                            }
                            else {

                                FirebaseDatabase.getInstance().getReference().child("users").child(couCode+number).setValue(couCode+number);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i=new Intent(phoneVerification.this,otp.class);
                            Pair[] pairs = new Pair[2];
                            pairs[0] = new Pair<View, String>(btnPlan, "btnPlan");
                            pairs[1] = new Pair<View, String>(btn, "btn");
                            i.putExtra("number",couCode+number);

                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(phoneVerification.this, pairs);
                            loading.setVisibility(View.INVISIBLE);
                            startActivity(i, options.toBundle());
//                            startActivity(i);
                        }
                    },1000);
//                    phoneNo.setError("");
                }
                else {
                    phoneNo.setError("Invalid phone no.");
                    loading.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

}