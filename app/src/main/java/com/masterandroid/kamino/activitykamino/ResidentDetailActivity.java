package com.masterandroid.kamino.activitykamino;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.masterandroid.kamino.R;
import com.masterandroid.kamino.viewmodel.ResidentViewModel;

public class ResidentDetailActivity extends AppCompatActivity {

    private ImageView imgDetail;
    private TextView txtNameDetail, txtHeightDetail, txtHairColorDetail, txtSkinColorDetail,
            txtEyeColorDetail, txtBirthDayDetail, txtGenderDetail;

    private ResidentViewModel residentViewModel;

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

        residentViewModel = new ViewModelProvider(this).get(ResidentViewModel.class);




        if (getIntent() != null) {
            int id = getIntent().getIntExtra("id", -1); // Koristite getIntExtra za Integer
            residentViewModel.checkInternetConnectionResidentDetails(id);

            residentViewModel.getResidentLiveData(Integer.valueOf(id)).observe(this, resident -> {
                if (resident != null) {
                    txtNameDetail.setText("Name: " + resident.getName());
                    txtHeightDetail.setText("Height: " + resident.getHeight());
                    txtHairColorDetail.setText("Hair Color: " + resident.getHairColor());
                    txtSkinColorDetail.setText("Skin Color: " + resident.getSkinColor());
                    txtEyeColorDetail.setText("Eye Color: " + resident.getEyeColor());
                    txtBirthDayDetail.setText("Birth Day: " + resident.getBirthYear());
                    txtGenderDetail.setText("Gender: " + resident.getGender());

                    Glide.with(this)
                            .load(resident.getImageUrl())
                            .placeholder(R.drawable.myprofile)
                            .error(R.drawable.solid_color_placeholder)
                            .into(imgDetail);

                }
            });
        }


    }




}