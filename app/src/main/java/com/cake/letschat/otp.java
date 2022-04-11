package com.cake.letschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;

public class otp extends AppCompatActivity {
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;


    private String mVerificationId;
    private static final String TAG = "MAIN_TAG";
    private FirebaseAuth firebaseAuth;
    public String phoneNumber;

    Button resendCodeTv;
    ImageView btn,btnPlan ;
    EditText codeEt;
    GifImageView loading;
    Animation buttonAnim;
    String otp;
    TextView no;

    FirebaseDatabase mDatabase ;
    DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }
        codeEt  =findViewById(R.id.codeEt);
        btn = findViewById(R.id.imageView5);
        btnPlan = findViewById(R.id.imageView6);
        resendCodeTv = findViewById(R.id.button);
        loading = findViewById(R.id.gifLoading);
        no=findViewById(R.id.textView3);

        loading.setVisibility(View.INVISIBLE);

        firebaseAuth =FirebaseAuth.getInstance();

        phoneNumber = getIntent().getStringExtra("number").trim();
        String phone =phoneNumber;
        no.setText(phone);

        mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                Toast.makeText(otp.this , ""+e.getMessage() ,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(@NonNull @NotNull String verificationId, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, forceResendingToken);
                Log.d(TAG , "onCodeSent:"+verificationId);
                mVerificationId =verificationId;
                forceResendingToken=token;
                Toast.makeText(otp.this , "verification code sent....", Toast.LENGTH_SHORT).show();

            }
        };
        mDatabase = FirebaseDatabase.getInstance();
        startPhoneNumberVerificatio(phone);



        buttonAnim = AnimationUtils.loadAnimation(this,R.anim.button_anim);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.startAnimation(buttonAnim);
                btnPlan.startAnimation(buttonAnim);
                otp = codeEt.getText().toString();
                if (otp.length() == 6){
                    loading.setVisibility(View.VISIBLE);
                    verifyPhoneNumberWithCode(mVerificationId , otp);
                     }
                else {
                    codeEt.setError("Invalid otp");
                }
            }
        });
        btnPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.startAnimation(buttonAnim);
                btnPlan.startAnimation(buttonAnim);
                otp = codeEt.getText().toString();
                if (otp.length() ==6){
                    verifyPhoneNumberWithCode(mVerificationId , otp);
                    loading.setVisibility(View.VISIBLE);
                       }
                else {
                    codeEt.setError("Invalid otp");
                    loading.setVisibility(View.INVISIBLE);
                }
            }
        });
        resendCodeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resendCodeTv.startAnimation(buttonAnim);
                resendVerificationCode(phone , forceResendingToken);

            }


        });


    }
    private void resendVerificationCode(String phone, PhoneAuthProvider.ForceResendingToken token) {
        Toast.makeText(otp.this , "resending code",Toast.LENGTH_SHORT).show();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L , TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mcallbacks)
                        .setForceResendingToken(token)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        loading.setVisibility(View.INVISIBLE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId ,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void startPhoneNumberVerificatio(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L , TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mcallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String phone =firebaseAuth.getCurrentUser().getPhoneNumber();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i=new Intent(otp.this,photoName.class);
                                Pair[] pairs = new Pair[2];
                                pairs[0] = new Pair<View, String>(btnPlan, "btnPlan");
                                pairs[1] = new Pair<View, String>(btn, "btn");
                                i.putExtra("number",phoneNumber);


                                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(otp.this, pairs);
                                startActivity(i, options.toBundle());
                                finish();
//                            startActivity(i);
                            }
                        },1000);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                      loading.setVisibility(View.INVISIBLE);
                        Toast.makeText(otp.this , "" +e.getMessage() ,Toast.LENGTH_SHORT).show();

                    }
                });
    }
}