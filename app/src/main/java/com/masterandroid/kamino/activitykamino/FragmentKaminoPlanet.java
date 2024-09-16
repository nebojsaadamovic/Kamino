package com.masterandroid.kamino.activitykamino;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.masterandroid.kamino.R;
import com.masterandroid.kamino.viewmodel.PlanetViewModel;

public class FragmentKaminoPlanet extends Fragment {
    private TextView planetNameTextView;
    private TextView rotationTextView;
    private TextView orbitalTextView;
    private TextView diameterTextView;
    private TextView climateTextView;
    private TextView gravityTextView;
    private TextView terrainTextView;
    private TextView surfaceTextView;
    private TextView planetPopulationTextView;
    private TextView likeTextView;
    private Button buttonLike;
    private PlanetViewModel planetViewModel;
    private Integer likes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kamino, container, false);
        planetViewModel = new ViewModelProvider(this).get(PlanetViewModel.class);


        planetNameTextView = view.findViewById(R.id.name_id);
        rotationTextView = view.findViewById(R.id.rotation_id);
        orbitalTextView = view.findViewById(R.id.orbital_id);
        diameterTextView = view.findViewById(R.id.diameter_id);
        climateTextView = view.findViewById(R.id.climate_id);
        gravityTextView = view.findViewById(R.id.gravity_id);
        terrainTextView = view.findViewById(R.id.terrain_id);
        surfaceTextView = view.findViewById(R.id.surface_water_id);
        planetPopulationTextView = view.findViewById(R.id.population_id);
        likeTextView = view.findViewById(R.id.likes_id);
        buttonLike = view.findViewById(R.id.btnlike_id);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String planetName = bundle.getString("planetName");
            String rotation = bundle.getString("rotation");
            String orbital = bundle.getString("orbital");
            String diameter = bundle.getString("diameter");
            String climate = bundle.getString("climate");
            String gravity = bundle.getString("gravity");
            String terrain = bundle.getString("terrain");
            String surface = bundle.getString("surface");
            String planetPopulation = bundle.getString("planetPopulation");
            likes = bundle.getInt("likes");

            if (planetName != null) {
                planetNameTextView.setText("Kamino: " + planetName);
            }
            if (rotation != null) {
                rotationTextView.setText("Rotation Period: " + rotation);
            }
            if (orbital != null) {
                orbitalTextView.setText("Orbital Period: " + orbital);
            }
            if (diameter != null) {
                diameterTextView.setText("Diameter: " + diameter);
            }
            if (climate != null) {
                climateTextView.setText("Climate: " + climate);
            }
            if (gravity != null) {
                gravityTextView.setText("Gravity: " + gravity);
            }
            if (terrain != null) {
                terrainTextView.setText("Terrain: " + terrain);
            }
            if (surface != null) {
                surfaceTextView.setText("Surface Water: " + surface);
            }
            if (planetPopulation != null) {
                planetPopulationTextView.setText("Population: " + planetPopulation);
            }
            if (likes != null) {
                likeTextView.setText("Likes: " + likes);
            }
            setLikeButtonVisibility(true);
        } else {
            setLikeButtonVisibility(false);
        }

        planetViewModel.getHasLiked().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hasLiked) {
                Integer countLikes= planetViewModel.getCountLike().getValue();
                if (hasLiked != null && hasLiked && countLikes !=null) {
                    buttonLike.setEnabled(false);
                } else {
                    buttonLike.setEnabled(true);
                }
            }
        });


        planetViewModel.getCountLike().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer likesCount) {
                if (likesCount != null) {
                    likeTextView.setText("Likes: " + likesCount);
                }
            }
        });


        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean hasLiked = planetViewModel.getHasLiked().getValue();
                if (Boolean.FALSE.equals(hasLiked)) {
                    planetViewModel.likePlanet();
                } else {
                    Toast.makeText(getContext(), "Već ste jednom lajkovali", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    public void setLikeButtonVisibility(boolean visible) {
        if (buttonLike != null) {
            if (visible) {
                buttonLike.setVisibility(View.VISIBLE);
            } else {
                buttonLike.setVisibility(View.GONE);
            }
        }
    }
}
