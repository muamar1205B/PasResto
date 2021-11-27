package com.example.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RestoAdapter adapter;
    ArrayList<RestoModel> restolist;
    ImageButton mProfilebtn;
    Button bFavbtn;
    TextView tvRestolist, tvUsername;
    View line;
    EditText etSearch;
    String API = "https://restaurant-api.dicoding.dev/list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfilebtn = findViewById(R.id.profilebtn);
        bFavbtn = findViewById(R.id.favBtn);
        tvRestolist = findViewById(R.id.restolist);
        line = findViewById(R.id.line);
        tvUsername = findViewById(R.id.username);
        etSearch = findViewById(R.id.searchtext);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = findViewById(R.id.restaurants);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        restolist = new ArrayList<>();

        getData();

        user.reload();

        mProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditActivity.class));
            }
        });

        bFavbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
            }
        });

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                if (user != null) {
                    String name = user.getDisplayName();
                    String uid = user.getUid();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        tvUsername.setText(name);
                                        Log.d("TEST", "User profile updated.");
                                    }
                                }
                            });
                }
            }
        }, 500);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("KAS", String.valueOf(s));
                API = "https://restaurant-api.dicoding.dev/search?q=".concat(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (API == "https://restaurant-api.dicoding.dev/search?q="){
                    restolist.clear();
                    API = "https://restaurant-api.dicoding.dev/list";
                }
                getData();
            }
        });
    }


    private void getData() {
        AndroidNetworking.get(API)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            restolist.clear();
                            JSONArray result = response.getJSONArray("restaurants");
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject resultObj = result.getJSONObject(i);

                                String id = resultObj.getString("id");
                                String name = resultObj.getString("name");
                                String description = resultObj.getString("description");
                                String pictureId = "https://restaurant-api.dicoding.dev/images/large/".concat(resultObj.getString("pictureId"));
                                String city = resultObj.getString("city");

                                restolist.add(new RestoModel(id, name, description, pictureId, city));
                                adapter = new RestoAdapter(restolist, getApplicationContext());
                                recyclerView.setAdapter(adapter);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                    }
                });
    }


    @Override
        public void onBackPressed ()
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    }