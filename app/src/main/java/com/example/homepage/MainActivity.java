package com.example.homepage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RestoAdapter adapter;
    ArrayList<RestoModel> restolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.restaurants);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        restolist = new ArrayList<>();

        getData();

        adapter = new RestoAdapter(restolist, this);
        recyclerView.setAdapter(adapter);

    }


    private void getData() {
        AndroidNetworking.get("https://restaurant-api.dicoding.dev/list")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray result = response.getJSONArray("restaurants");
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject resultObj = result.getJSONObject(i);

                                String id = resultObj.getString("id");
                                String name = resultObj.getString("name");
                                String description = resultObj.getString("description");
                                String pictureId = "https://restaurant-api.dicoding.dev/images/medium/".concat(resultObj.getString("pictureId"));
                                String city = resultObj.getString("city");

                                restolist.add(new RestoModel(id, name, description, pictureId, city));
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

//        private void getData{
//            AndroidNetworking.get("https://restaurant-api.dicoding.dev/detail/:id")
//                    .build()
//                    .getAsJSONObject(new JSONObjectRequestListener() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                JSONArray result = response.getJSONArray("restaurants");
//                                for (int i = 0; i < result.length(); i++) {
//                                    JSONObject resultObj = result.getJSONObject(i);
//
//                                    String menus = resultObj.getString("menus");
//                                    String customerReviews = resultObj.getString("customerReviews")
//                            }
//                        }
//
//                        @Override
//                        public void onError(ANError anError) {
//                            anError.printStackTrace();
//                        }
//                    })
//        }
    }
}