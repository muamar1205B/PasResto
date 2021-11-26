package com.example.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditActivity extends AppCompatActivity {
    Button mLogoutBtn, mSaveChanges;
    EditText etEditName;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        viewInitializations();
    }

    private void viewInitializations() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mLogoutBtn = findViewById(R.id.logoutBtn);
        mSaveChanges = findViewById(R.id.savechangesBtn);
        etEditName = findViewById(R.id.editName);

        FirebaseApp.initializeApp(this);
        fAuth = FirebaseAuth.getInstance();


        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

            mSaveChanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String fullname = etEditName.getText().toString().trim();

                    if (TextUtils.isEmpty(fullname)) {
                        etEditName.setError("Name is Required.");
                        return;
                    }
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(fullname)
                            .build();
                    user.updateProfile(profileUpdates);
                    fAuth.getCurrentUser().updateProfile(profileUpdates);
                    Toast.makeText(EditActivity.this, "User Updated.", Toast.LENGTH_SHORT).show();
                    user.reload();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                        }

                    },300);
                }
        });
    }
}
