package com.masterandroid.kamino.activitykamino;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.masterandroid.kamino.R;
import com.masterandroid.kamino.data.api.StarWarsApiService;
import com.masterandroid.kamino.data.model.Planet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentKamino extends Fragment {
    private TextView textViewPlanetName;
    private TextView textViewPlanetRotation;
    private TextView textViewPlanetOrbital;
    private TextView textViewPlanetDiameter;
    private TextView textViewPlanetClimate;
    private TextView textViewPlanetGravity;
    private TextView textViewPlanetTerrain;
    private TextView textViewPlanetSurfaceWater;
    private TextView textViewPlanetPopulation;
    private TextView textViewPlanetLikes;
    private ImageView imgViewPlanet;
    Button buttonLike;
    int likes;
    private StarWarsApiService starWarsApiService;
    private static final String PLANET_ID = "10";
    private boolean showData = true;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kamino, container, false);

        textViewPlanetName = view.findViewById(R.id.name_id);
        textViewPlanetRotation = view.findViewById(R.id.rotation_id);
        textViewPlanetOrbital = view.findViewById(R.id.orbital_id);
        textViewPlanetDiameter = view.findViewById(R.id.diameter_id);
        textViewPlanetClimate = view.findViewById(R.id.climate_id);
        textViewPlanetGravity = view.findViewById(R.id.gravity_id);
        textViewPlanetTerrain = view.findViewById(R.id.terrain_id);
        textViewPlanetSurfaceWater = view.findViewById(R.id.surface_water_id);
        textViewPlanetPopulation = view.findViewById(R.id.population_id);
        textViewPlanetLikes = view.findViewById(R.id.likes_id);
        imgViewPlanet = view.findViewById(R.id.imageViewPlanet_id);
        buttonLike = view.findViewById(R.id.btnlike_id);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-anon-59e7f97310-starwars2.apiary-mock.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        starWarsApiService = retrofit.create(StarWarsApiService.class);

        if (getArguments() != null) {
            showData = getArguments().getBoolean("showData", true);
        }
        if (showData) {
            getKaminoPlanet();
        } else {
            clearPlanetData();
        }

        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likes++;
                textViewPlanetLikes.setText("Likes: " + likes); // Ensure likes is an integer and not a resource ID
            }
        });

        return view;
    }


    private void getKaminoPlanet() {
        Call<Planet> call = starWarsApiService.getKaminoPlanet(Integer.parseInt(PLANET_ID));
        call.enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(@NonNull Call<Planet> call, @NonNull Response<Planet> response) {
                if (response.isSuccessful()) {
                    Planet planet = response.body();
                    if (planet != null) {
                        textViewPlanetName.setText("Name: " + planet.getName());
                        textViewPlanetRotation.setText("Rotation Period: " + planet.getRotationPeriod());
                        textViewPlanetOrbital.setText("Orbital Period: " + planet.getOrbitalPeriod());
                        textViewPlanetDiameter.setText("Diameter: " + planet.getDiameter());
                        textViewPlanetClimate.setText("Climate: " + planet.getClimate());
                        textViewPlanetGravity.setText("Gravity: " + planet.getGravity());
                        textViewPlanetTerrain.setText("Terrain: " + planet.getTerrain());
                        textViewPlanetSurfaceWater.setText("Surface Water: " + planet.getSurfaceWater());
                        textViewPlanetPopulation.setText("Population: " + planet.getPopulation());
                        likes= planet.getLikes();
                        textViewPlanetLikes.setText("Likes: "+likes);
                        String imageUrl = planet.getImageUrl(); // Your URL here
                       // String testUrl = "https://via.placeholder.com/150";
                        Glide.with(getContext())
                                .load(imageUrl)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.solid_color_placeholder)
                                        .error(R.drawable.kaminostar))
                                .into(imgViewPlanet);
                    } else {
                        Log.d("FragmentKamino", "Response body is null");
                    }
                } else {
                    Log.d("FragmentKamino", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Planet> call, Throwable t) {
                Log.e("FragmentKamino", "API call failure", t);
            }
        });
    }



    private void clearPlanetData() {
        textViewPlanetName.setText("");
        textViewPlanetRotation.setText("");
    }
}

