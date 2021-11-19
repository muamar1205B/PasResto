package com.example.homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FavoriteAdapter adapter;
    List<RestoModel> restaurantlist;
    Realm realm;
    RealmHelper realmHelper;
    ImageButton mProfilebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mProfilebtn = findViewById(R.id.profilebtn);

        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.dashboard_rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        restaurantlist = new ArrayList<>();

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);

        realmHelper = new RealmHelper(realm);
        restaurantlist = new ArrayList<>();

        restaurantlist = realmHelper.getAllRestaurant();

        adapter = new FavoriteAdapter(restaurantlist, getApplicationContext());
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        mProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditActivity.class));
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}