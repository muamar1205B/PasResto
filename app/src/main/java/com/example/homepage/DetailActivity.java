package com.example.homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

public class DetailActivity extends AppCompatActivity {
    String id, name, description, pictureId, city, address;
    ImageView ivPicture;
    TextView tvName, tvDescription, tvAlamat, tvCategoryValues, tvKota;
    Model teamModel;
    Bundle bundle;
    ImageButton ibFav;
    Realm realm;
    RealmHelper realmHelper;
    RealmList<String> foodsString = new RealmList<>();
    RealmList<String> drinksString = new RealmList<>();
    RealmList<String> categoriesString = new RealmList<>();
    private String API_URL;
    private MenuAdapter foodAdapter, drinksAdapter, categoryAdapter;
    private RecyclerView rv_food, rv_drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide();

        tvName = findViewById(R.id.detailName);
        tvDescription = findViewById(R.id.detailDesc);
        tvCategoryValues = findViewById(R.id.detailCategoryValue);
        tvKota = findViewById(R.id.detailCity);
        tvAlamat = findViewById(R.id.detailAlamat);
        ivPicture = findViewById(R.id.detailImage);
        ibFav = findViewById(R.id.favImageBtn);
        rv_food = findViewById(R.id.rv_foods);
        rv_drink = findViewById(R.id.rv_drinks);

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);

        bundle = getIntent().getExtras();
        if (bundle != null){
            id = bundle.getString("id");
            name = bundle.getString("name");
            description = bundle.getString("description");
            pictureId = bundle.getString("pictureId");
            city = bundle.getString("city");

            List<RestoModel> list = realmHelper.getRestoById(id);

            API_URL = "https://restaurant-api.dicoding.dev/detail/"+id;
            getNewData();

            // Set up
            Realm.init(this);
            RealmConfiguration configuration1 = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
            Realm realm1 = Realm.getInstance(configuration1);
            realmHelper = new RealmHelper(realm1);
        }

        tvName.setText(name);
        tvDescription.setText(description);
        tvKota.setText(city);
        Glide.with(getApplicationContext())
                .load(pictureId)
                .into(ivPicture);

        AtomicReference<RestoModel> model = new AtomicReference<>(realm.where(RestoModel.class).equalTo("id", id).findFirst());
        Log.d("TAG", String.valueOf(model));
        if(model.get() == null){
            ibFav.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        }else {
            ibFav.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F3C456")));
        }

        ibFav.setOnClickListener(v -> {
            RestoModel restaurantModel = new RestoModel(id, name, description, city, address, pictureId, categoriesString, foodsString, drinksString);
            model.set(realm.where(RestoModel.class).equalTo("id", id).findFirst());

            if(model.get() == null){
                realmHelper.save(restaurantModel);
                ibFav.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F3C456")));
                Toast.makeText(getApplicationContext(), "Added to your favorite", Toast.LENGTH_SHORT).show();
            } else {
                realmHelper.delete(id);
                ibFav.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                Toast.makeText(getApplicationContext(), "Removed from your favorite", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void getNewData() {
        AndroidNetworking.get(API_URL)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject teamsArray = response.getJSONObject("restaurant");
                            JSONObject menus = teamsArray.getJSONObject("menus");
                            JSONArray customerReviews = teamsArray.getJSONArray("customerReviews");
                            JSONArray categories = teamsArray.getJSONArray("categories");
                            JSONArray foods = menus.getJSONArray("foods");
                            JSONArray drinks = menus.getJSONArray("drinks");

                            address = teamsArray.getString("address");
                            tvAlamat.setText(address);

                            for (int i = 0; i < foods.length(); i++) {
                                JSONObject object = foods.getJSONObject(i);
                                String name = object.getString("name");
                                foodsString.add(name);
                            }

                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject object = categories.getJSONObject(i);
                                String name = object.getString("name");
                                categoriesString.add(name);
                            }

                            for (int i = 0; i < drinks.length(); i++) {
                                JSONObject object = drinks.getJSONObject(i);
                                String name = object.getString("name");
                                drinksString.add(name);
                            }

                            if (categoriesString.size() == 2) tvCategoryValues.setText(categoriesString.get(0) + ", " + categoriesString.get(1));
                            else tvCategoryValues.setText(categoriesString.get(0));

                            int numberOfColumns = 2;
                            foodAdapter = new MenuAdapter(foodsString);
                            RecyclerView.LayoutManager foodlayoutmanager = new LinearLayoutManager(getApplicationContext());
                            rv_food.setLayoutManager(foodlayoutmanager);
                            rv_food.setAdapter(foodAdapter);
                            rv_food.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));

                            drinksAdapter = new MenuAdapter(drinksString);
                            RecyclerView.LayoutManager drinklayoutmanager = new LinearLayoutManager(getApplicationContext());
                            rv_drink.setLayoutManager(drinklayoutmanager);
                            rv_drink.setAdapter(drinksAdapter);
                            rv_drink.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));

                            teamModel = (Model) new RestoModel(id, name, description, city, address, pictureId, categoriesString, foodsString, drinksString);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

}
