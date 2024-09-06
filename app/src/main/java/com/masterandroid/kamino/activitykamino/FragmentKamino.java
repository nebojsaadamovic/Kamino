package com.masterandroid.kamino.activitykamino;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.masterandroid.kamino.R;
import com.masterandroid.kamino.data.model.Planet;
import com.masterandroid.kamino.viewmodel.FragmentKaminoViewModel;

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

    private FragmentKaminoViewModel kaminoViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        kaminoViewModel = new ViewModelProvider(this).get(FragmentKaminoViewModel.class);

        if (getArguments() != null) {
            boolean showData = getArguments().getBoolean("showData", true);
            kaminoViewModel.setShowData(showData);
        }

        kaminoViewModel.getPlanetLiveData().observe(getViewLifecycleOwner(), planet -> {
            if (planet != null) {
                updateUI(planet);
            } else {
                clearPlanetData();
            }
        });

        kaminoViewModel.getLikesLiveData().observe(getViewLifecycleOwner(), likes -> {
            textViewPlanetLikes.setText("Likes: " + likes);
        });

        kaminoViewModel.getHasLikedLiveData().observe(getViewLifecycleOwner(), hasLiked -> {
            if (hasLiked != null && hasLiked) {
                buttonLike.setEnabled(false); // Disable button if already liked
                Toast.makeText(getContext(), "You have already liked once", Toast.LENGTH_SHORT).show();
            }
        });

        buttonLike.setOnClickListener(v -> {
            int planetId = 10;
            kaminoViewModel.likePlanet(planetId);
        });

        return view;
    }

    private void updateUI(Planet planet) {
        textViewPlanetName.setText("Name: " + planet.getName());
        textViewPlanetRotation.setText("Rotation Period: " + planet.getRotationPeriod());
        textViewPlanetOrbital.setText("Orbital Period: " + planet.getOrbitalPeriod());
        textViewPlanetDiameter.setText("Diameter: " + planet.getDiameter());
        textViewPlanetClimate.setText("Climate: " + planet.getClimate());
        textViewPlanetGravity.setText("Gravity: " + planet.getGravity());
        textViewPlanetTerrain.setText("Terrain: " + planet.getTerrain());
        textViewPlanetSurfaceWater.setText("Surface Water: " + planet.getSurfaceWater());
        textViewPlanetPopulation.setText("Population: " + planet.getPopulation());
        Glide.with(requireContext())
                .load(planet.getImageUrl())
                .apply(new RequestOptions()
                        .error(R.drawable.solid_color_placeholder))
                .into(imgViewPlanet);
        buttonLike.setVisibility(View.VISIBLE);
        textViewPlanetLikes.setVisibility(View.VISIBLE);
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
}
