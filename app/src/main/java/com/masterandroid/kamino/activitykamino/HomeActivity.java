package com.masterandroid.kamino.activitykamino;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.masterandroid.kamino.R;
import com.masterandroid.kamino.data.model.Planet;
import com.masterandroid.kamino.viewmodel.PlanetViewModel;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageView imgKamino;
    private PlanetViewModel planetViewModel;
    FrameLayout frameLayout;
    private ImageView planetImageView;  // Dodaj ImageView za sliku

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamino);


        imgKamino=findViewById(R.id.imgkamino_id);
        toolbar= findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerlayout_id);
        navigationView = findViewById(R.id.navigationview_id);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        frameLayout=findViewById(R.id.framelayout_id);

        planetViewModel=new ViewModelProvider(this).get(PlanetViewModel.class);

        planetViewModel.getImage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String imageUrl) {
                Glide.with(HomeActivity.this)
                        .load(imageUrl)
                        .placeholder(R.drawable.kaminostar)
                        .error(R.drawable.world)
                        .into(imgKamino);
            }
        });

        planetViewModel.fetchImageHeaderApi();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id == R.id.residents_id){
                    getSupportFragmentManager().beginTransaction().replace(frameLayout.getId(), new FragmentResident()).commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Unknow page", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });


        planetViewModel.getShowData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean showData) {
                if (showData) {
                    planetViewModel.getPlanetDetails().observe(HomeActivity.this, new Observer<Planet>() {
                        @Override
                        public void onChanged(Planet planet) {
                            if (planet != null) {
                                FragmentKaminoPlanet fragmentKaminoPlanet = new FragmentKaminoPlanet();
                                Bundle bundle = new Bundle();
                                bundle.putString("planetName", planet.getName());
                                bundle.putString("rotation", planet.getRotationPeriod());
                                bundle.putString("orbital", planet.getOrbitalPeriod());
                                bundle.putString("diameter", planet.getDiameter());
                                bundle.putString("climate", planet.getClimate());
                                bundle.putString("gravity", planet.getGravity());
                                bundle.putString("terrain", planet.getTerrain());
                                bundle.putString("surface", planet.getSurfaceWater());
                                bundle.putString("planetPopulation", planet.getPopulation());
                                bundle.putInt("likes", planet.getLikes());
                                bundle.putString("imageUrl", planet.getImageUrl());
                                fragmentKaminoPlanet.setArguments(bundle);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(frameLayout.getId(), fragmentKaminoPlanet)
                                        .commit();
                            } else {
                                Log.d("HomeActivity", "No planet details to display.");
                            }
                        }
                    });
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .replace(frameLayout.getId(), new FragmentKaminoPlanet())
                            .commit();
                }
            }
        });





        imgKamino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planetViewModel.checkInternetConnectionFetchLike(10);
                Animation animation= AnimationUtils.loadAnimation(HomeActivity.this, R.anim.img_zoomin);
                imgKamino.startAnimation(animation);
            }
        });



    }







}
