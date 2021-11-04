package com.example.homepage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    String id, name, description, pictureId, city;
    ImageView ivPicture;
    TextView tvName, tvDescription, tvAlamat;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide();

        tvName = findViewById(R.id.detail_namaRestoran_txt);
        tvDescription = findViewById(R.id.detail_deskripsi_txt);
        tvAlamat = findViewById(R.id.detail_alamat_txt);
        ivPicture = findViewById(R.id.detail_detailImage);

        bundle = getIntent().getExtras();
        if (bundle != null){
            id = bundle.getString("id");
            name = bundle.getString("name");
            description = bundle.getString("description");
            pictureId = bundle.getString("pictureId");
            city = bundle.getString("city");
        }

        tvName.setText(name);
        tvDescription.setText(description);
        tvAlamat.setText(city);
        Glide.with(getApplicationContext())
                .load(pictureId)
                .into(ivPicture);

    }
}