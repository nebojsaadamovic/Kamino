package com.masterandroid.kamino.activitykamino;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.masterandroid.kamino.R;
import com.masterandroid.kamino.viewmodel.ResidentDetailViewModel;

public class ResidentDetailActivity extends AppCompatActivity {

    private ImageView imgDetail;
    private TextView txtNameDetail, txtHeightDetail, txtHairColorDetail, txtSkinColorDetail,
            txtEyeColorDetail, txtBirthDayDetail, txtGenderDetail;

    private ResidentDetailViewModel viewModel;

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

        viewModel = new ViewModelProvider(this).get(ResidentDetailViewModel.class);


        if (getIntent() != null) {
            String name = getIntent().getStringExtra("name");
            String height = getIntent().getStringExtra("height");
            String hairColor = getIntent().getStringExtra("hair_color");
            String skinColor = getIntent().getStringExtra("skin_color");
            String eyeColor = getIntent().getStringExtra("eye_color");
            String birthDay = getIntent().getStringExtra("birth_day");
            String gender = getIntent().getStringExtra("gender");
            String imageUrl = getIntent().getStringExtra("image_url");

            viewModel.setResidentDetails(name, height, hairColor, skinColor, eyeColor, birthDay, gender, imageUrl);
        }

        viewModel.getName().observe(this, name -> txtNameDetail.setText("Name: " + name));
        viewModel.getHeight().observe(this, height -> txtHeightDetail.setText("Height: " + height));
        viewModel.getHairColor().observe(this, hairColor -> txtHairColorDetail.setText("Hair Color: " + hairColor));
        viewModel.getSkinColor().observe(this, skinColor -> txtSkinColorDetail.setText("Skin Color: " + skinColor));
        viewModel.getEyeColor().observe(this, eyeColor -> txtEyeColorDetail.setText("Eye Color: " + eyeColor));
        viewModel.getBirthDay().observe(this, birthDay -> txtBirthDayDetail.setText("Birth Day: " + birthDay));
        viewModel.getGender().observe(this, gender -> txtGenderDetail.setText("Gender: " + gender));
        viewModel.getImageUrl().observe(this, imageUrl -> {
            Glide.with(getApplicationContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.myprofile)
                    .error(R.drawable.solid_color_placeholder)
                    .into(imgDetail);
        });
    }
}
