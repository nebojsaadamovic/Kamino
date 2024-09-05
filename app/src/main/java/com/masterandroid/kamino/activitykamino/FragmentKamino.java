package com.masterandroid.kamino.activitykamino;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.masterandroid.kamino.R;
import com.masterandroid.kamino.data.api.Constants;
import com.masterandroid.kamino.data.api.StarWarsApiService;
import com.masterandroid.kamino.data.model.LikeRequest;
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
    private Button buttonLike;
    private boolean hasLiked = false;
    private int likes = 0;
    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_HAS_LIKED = "has_liked";
    private static final String KEY_LIKES_COUNT = "likes_count";
    private static final String PLANET_ID = "10";
    private boolean showData = true;
    private StarWarsApiService starWarsApiService;
    String imageUrl;

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
                .baseUrl(Constants.BASE_URL)
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


        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        hasLiked = prefs.getBoolean(KEY_HAS_LIKED, false);
        likes = prefs.getInt(KEY_LIKES_COUNT, 0);
        updateLikesUI();


        buttonLike.setOnClickListener(v -> {
            if (hasLiked) {
                likePlanet();
            } else {
                Toast.makeText(getContext(), "You have already liked once", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }


    private void getKaminoPlanet() {
        buttonLike.setVisibility(View.VISIBLE);
        textViewPlanetLikes.setVisibility(View.VISIBLE);
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
                        imageUrl = planet.getImageUrl();

                        if (getContext() != null) {
                            Glide.with(getContext())
                                    .load(imageUrl)
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.solid_color_placeholder)
                                            .error(R.drawable.kaminostar))
                                    .into(imgViewPlanet);
                        }


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
        textViewPlanetName.setVisibility(View.INVISIBLE);
        textViewPlanetRotation.setVisibility(View.INVISIBLE);
        textViewPlanetOrbital.setVisibility(View.INVISIBLE);
        textViewPlanetDiameter.setVisibility(View.INVISIBLE);
        textViewPlanetClimate.setVisibility(View.INVISIBLE);
        textViewPlanetGravity.setVisibility(View.INVISIBLE);
        textViewPlanetTerrain.setVisibility(View.INVISIBLE);
        textViewPlanetSurfaceWater.setVisibility(View.INVISIBLE);
        textViewPlanetPopulation.setVisibility(View.INVISIBLE);
        textViewPlanetLikes.setVisibility(View.INVISIBLE);
        buttonLike.setVisibility(View.INVISIBLE);
    }



    private void likePlanet() {
        LikeRequest likeRequest = new LikeRequest(Integer.parseInt(PLANET_ID));
        Call<Void> call = starWarsApiService.likePlanet(Integer.parseInt(PLANET_ID), likeRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    hasLiked = true;
                    likes++;
                    SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(KEY_HAS_LIKED, hasLiked);
                    editor.putInt(KEY_LIKES_COUNT, likes);
                    editor.apply();
                    updateLikesUI();
                    hasLiked = false;
                    //buttonLike.setEnabled(false);
                } else {
                    Log.d("FragmentKamino", "Failed to like planet: " + response.code());
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("FragmentKamino", "Failed to make like request", t);
            }
        });
    }


    private void updateLikesUI() {
        textViewPlanetLikes.setText("Likes: " + likes);
    }
}

