package com.example.homepage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailActivity extends AppCompatActivity {
    String id, name, description, pictureId, city;
    ImageView ivPicture;
    TextView tvName, tvDescription, tvAlamat;
    Bundle bundle;
    ImageButton ibFav;
    Realm realm;
    RealmHelper realmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide();

        tvName = findViewById(R.id.detailName);
        tvDescription = findViewById(R.id.detailDesc);
        tvAlamat = findViewById(R.id.detailCity);
        ivPicture = findViewById(R.id.detailImage);
        ibFav = (ImageButton)findViewById(R.id.favImageBtn);

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
        }

        AtomicReference<RestoModel> model = new AtomicReference<>(realm.where(RestoModel.class).equalTo("name", name).findFirst());
        Log.d("TAG", String.valueOf(model));
        if(model.get() == null){
            ibFav.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        }else {
            ibFav.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F3C456")));
        }


        tvName.setText(name);
        tvDescription.setText(description);
        tvAlamat.setText(city);
        Glide.with(getApplicationContext())
                .load(pictureId)
                .into(ivPicture);

        ibFav.setOnClickListener(v -> {
            RestoModel restaurantModel = new RestoModel(id, name, description, pictureId, city);
            model.set(realm.where(RestoModel.class).equalTo("name", name).findFirst());

            if(model.get() == null){
                realmHelper.save(restaurantModel);
                ibFav.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                Toast.makeText(getApplicationContext(), "Added to your favorite", Toast.LENGTH_SHORT).show();
            } else {
                realmHelper.delete(id);
                ibFav.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F3C456")));
                Toast.makeText(getApplicationContext(), "Removed from your favorite", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
