package com.example.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "EditActivity";
    Button mLogoutBtn, mSaveChanges;
    EditText etEditName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        viewInitializations();
    }

    private void viewInitializations() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();

        mLogoutBtn = findViewById(R.id.logoutBtn);
        mSaveChanges = findViewById(R.id.savechangesBtn);
        etEditName = findViewById(R.id.editName);

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

            }
        });
    }
}
