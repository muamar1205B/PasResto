package com.example.homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
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
    SearchView svText;
    TextView tvRestolist;
    View line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        svText = findViewById(R.id.searchtext);
        mProfilebtn = findViewById(R.id.profilebtn);
        bFavbtn = findViewById(R.id.favBtn);
        tvRestolist = findViewById(R.id.restolist);
        line = findViewById(R.id.line);

        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.restaurants);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        restolist = new ArrayList<>();

        getData();

        adapter = new RestoAdapter(restolist, this);
        recyclerView.setAdapter(adapter);

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

        svText.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvRestolist.setVisibility(View.INVISIBLE);
                line.setVisibility(View.INVISIBLE);
            }
        });

        svText.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tvRestolist.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
                return false;
            }
        });


        //SEARCH
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.resto_menu, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();

        svText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        svText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                try {
                    adapter.getFilter().filter(s);
                } catch (Exception e) {
                    Log.d("error", "" + e.toString());
                }
                return false;
            }
        });
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
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//    }

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