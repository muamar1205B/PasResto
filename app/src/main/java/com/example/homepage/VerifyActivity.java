package com.example.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyActivity extends AppCompatActivity {

    Button verifyBtn, continueBtn;
    TextView tvWrongpassword;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    boolean emailVerified = user.isEmailVerified();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        verifyBtn = findViewById(R.id.verifybutton);
//        continueBtn = findViewById(R.id.continuebutton);
        tvWrongpassword = findViewById(R.id.wrongpassword);

        getSupportActionBar().hide();

        tvWrongpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                user.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            private static final String TAG = "Test";

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                verifyBtn.setEnabled(true);

                                if (task.isSuccessful()) {
                                    Toast.makeText(VerifyActivity.this,
                                            "Verification email sent to " + user.getEmail(),
                                            Toast.LENGTH_SHORT).show();
//                                    addAuthListener();
                                } else {
                                    Log.e(TAG, "sendEmailVerification", task.getException());
                                    Toast.makeText(VerifyActivity.this,
                                            "Failed to send verification email.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

//                for(int i = 0; i < verified; i++){
//                    if (user.isEmailVerified()){
//                        Log.d("Verified", "Verified");
//                        startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
//                    }else{
//                        user.reload();
//                        Log.d("reloading", "Reloading");
//
//                    }
//                }
            }
        });

//        continueBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                user.reload();
//                if (user.isEmailVerified()) {
//                    startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
//                } else {
//                    Toast.makeText(VerifyActivity.this,
//                            "Email Not Verified",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }


//    private void addAuthListener() {
//        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                user.reload();
//                if (user != null) {
//                    if (user.isEmailVerified()) {
//                        Log.d("Verified", "Verified");
//                        startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
//                    }
//                }
//            }
//        });
//    }



    @Override
    protected void onResume() {
        super.onResume();
        user.reload();
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
//        user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    if (user.isEmailVerified()) {
                        Log.d("Verified", "Verified");
                        startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                    }
                }
            }
        }, 1000);
        Log.d("Test","Halo gan");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}

