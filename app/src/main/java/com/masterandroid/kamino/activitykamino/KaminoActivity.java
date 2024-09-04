package com.masterandroid.kamino.activitykamino;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.navigation.NavigationView;
import com.masterandroid.kamino.R;
import com.masterandroid.kamino.data.api.RetrofitClient;

import retrofit2.Retrofit;

public class KaminoActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView imgKamino;
    private boolean isExpanded = false;
    private final int HEIGHT_EXPAND_DP = 100;
    private static boolean showData = true; // Dodajte ovo za kontrolu prikaza podataka



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamino);

        imgKamino=findViewById(R.id.imgkamino_id);

        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerlayout_id);
        navigationView = findViewById(R.id.navigationview_id);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Retrofit retrofitClient = RetrofitClient.getClient();
        if (retrofitClient == null) {
            Log.e("QRGenerate", "Retrofit client is null");
            return;
        }else{
            Log.e("not", "Retrofit client is not null"+retrofitClient);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.residents_id) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new FragmentResident()).commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (id == R.id.home_id) {
                    Toast.makeText(getApplicationContext(), "Home click", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    Toast.makeText(getApplicationContext(), "Unknown click", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });


        imgKamino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateImageViewHeight();
                FragmentKamino fragmentKamino = new FragmentKamino();
                Bundle args = new Bundle();
                args.putBoolean("showData", showData);
                fragmentKamino.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout_id, fragmentKamino)
                        .commit();
                showData = !showData;
            }
        });




    }








    private void animateImageViewHeight() {
        final int dpToPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEIGHT_EXPAND_DP, getResources().getDisplayMetrics());
        final ViewGroup.LayoutParams layoutParams = imgKamino.getLayoutParams();

        // Determine the target height
        final int targetHeight = isExpanded ? layoutParams.height - dpToPx : layoutParams.height + dpToPx;

        // Create a ValueAnimator to animate the height change
        ValueAnimator animator = ValueAnimator.ofInt(layoutParams.height, targetHeight);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Update the height of the ImageView
                layoutParams.height = (int) animation.getAnimatedValue();
                imgKamino.setLayoutParams(layoutParams);
            }
        });

        animator.start();

        // Toggle the expanded state
        isExpanded = !isExpanded;
    }







}