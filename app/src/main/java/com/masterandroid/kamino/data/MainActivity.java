package com.masterandroid.kamino.data;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.masterandroid.kamino.R;
import com.masterandroid.kamino.data.api.RetrofitClient;
import com.masterandroid.kamino.data.api.StarWarsApiService;
import com.masterandroid.kamino.data.model.Planet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

        int planetId=10;  //ovdje ce button prosledjivati podatak
    private StarWarsApiService starWarsApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofitClient = RetrofitClient.getClient();
        if (retrofitClient == null) {
            Log.e("QRGenerate", "Retrofit client is null");
            return;
        }else{
            Log.e("not", "Retrofit client is not null"+retrofitClient);
        }
        starWarsApiService = retrofitClient.create(StarWarsApiService.class);
        getKaminoPlanet();//TODO

    }


    private void getKaminoPlanet() {//TODO
        Call<Planet> call = starWarsApiService.getKaminoPlanet(planetId);
        call.enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(@NonNull Call<Planet> call, @NonNull Response<Planet> response) {
                if (response.isSuccessful()) {
                    Planet planet1 = response.body();
                    if (planet1 != null) {
                        System.out.println(planet1.getName());//TODO
                        System.out.println(planet1.getRotationPeriod());//TODO
                    } else {
                        System.out.println("Response body is null");
                    }
                } else {
                    // Handle the case where the response is not successful
                    System.out.println("Response not successful: " + response.code());
                }
                }
            @Override
            public void onFailure(Call<Planet> call, Throwable t) {
                Log.e("API_CALL_FAILURE", "Failed to make API call", t);
            }
        });
    }





}