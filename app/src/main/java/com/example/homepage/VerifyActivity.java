package com.example.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyActivity extends AppCompatActivity {

    Button verifyBtn, continueBtn;
    TextView tvWrongpassword, tvWelcometext;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    boolean emailVerified = user.isEmailVerified();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        verifyBtn = findViewById(R.id.verifybutton);
//        continueBtn = findViewById(R.id.continuebutton);
        tvWrongpassword = findViewById(R.id.wrongpassword);
        tvWelcometext = findViewById(R.id.welcometext);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tvWrongpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.delete();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                    }
                },300);
//                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
//                finish();
//                overridePendingTransition(0, 0);
//                startActivity(getIntent());
//                overridePendingTransition(0, 0);
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
                                } else {
                                    Log.e(TAG, "sendEmailVerification", task.getException());
                                    Toast.makeText(VerifyActivity.this,
                                            "Failed to send verification email.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();
        user.reload();
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
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
                startActivity(new Intent(getApplicationContext(), VerifyActivity.class));
    }
}



