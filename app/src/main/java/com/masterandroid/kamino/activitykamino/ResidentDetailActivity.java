package com.masterandroid.kamino.activitykamino;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.masterandroid.kamino.R;

public class ResidentDetailActivity extends AppCompatActivity {

    private ImageView imgDetail, imgHomeworldDetail;
    private TextView txtNameDetail, txtHeightDetail, txtHairColorDetail, txtSkinColorDetail,
            txtEyeColorDetail, txtBirthDayDetail, txtGenderDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_detail);

        imgDetail = findViewById(R.id.img_detail);
        txtNameDetail = findViewById(R.id.txt_name_detail);
        txtHeightDetail = findViewById(R.id.txt_height_detail);
        txtHairColorDetail = findViewById(R.id.txt_hair_color_detail);
        txtSkinColorDetail = findViewById(R.id.txt_skin_color_detail);
        txtEyeColorDetail = findViewById(R.id.txt_eye_color_detail);
        txtBirthDayDetail = findViewById(R.id.txt_birth_day_detail);
        txtGenderDetail = findViewById(R.id.txt_gender_detail);
        imgHomeworldDetail = findViewById(R.id.img_homeworld_detail);

        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String height = intent.getStringExtra("height");
            String hairColor = intent.getStringExtra("hair_color");
            String skinColor = intent.getStringExtra("skin_color");
            String eyeColor = intent.getStringExtra("eye_color");
            String birthDay = intent.getStringExtra("birth_day");
            String gender = intent.getStringExtra("gender");
            String imageUrl = intent.getStringExtra("image_url");


            txtNameDetail.setText("Name: " + name);
            txtHeightDetail.setText("Height: " + height);
            txtHairColorDetail.setText("Hair Color: " + hairColor);
            txtSkinColorDetail.setText("Skin Color: " + skinColor);
            txtEyeColorDetail.setText("Eye Color: " + eyeColor);
            txtBirthDayDetail.setText("Birth Day: " + birthDay);
            txtGenderDetail.setText("Gender: " + gender);


            Glide.with(getApplicationContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.myprofile)
                    .error(R.drawable.solid_color_placeholder)
                    .into(imgDetail);


        }

    }
}
