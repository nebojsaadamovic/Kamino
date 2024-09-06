package com.masterandroid.kamino.activitykamino;

import android.animation.ValueAnimator;
import android.os.Bundle;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;
import com.masterandroid.kamino.R;
import com.masterandroid.kamino.viewmodel.KaminoActivityViewModel;

public class KaminoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ImageView imgKamino;
    private KaminoActivityViewModel kaminoViewModel;
    private final int HEIGHT_EXPAND_DP = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamino);

        imgKamino = findViewById(R.id.imgkamino_id);
        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerlayout_id);
        navigationView = findViewById(R.id.navigationview_id);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        kaminoViewModel = new ViewModelProvider(this).get(KaminoActivityViewModel.class);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.residents_id) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new FragmentResident()).commit();
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


                // Posmatranje promena u ViewModel-u i ažuriranje UI-a
                kaminoViewModel.getIsImageExpanded().observe(KaminoActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isExpanded) {
                        animateImageViewHeight(isExpanded);
                    }
                });

                kaminoViewModel.getShowData().observe(KaminoActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean showData) {
                        updateFragment(showData);
                    }
                });

                kaminoViewModel.toggleImageExpanded();
                kaminoViewModel.toggleShowData();

            }
        });
    }


    private void updateFragment(boolean showData) {
        FragmentKamino fragmentKamino = new FragmentKamino();
        Bundle args = new Bundle();
        args.putBoolean("showData", showData);
        fragmentKamino.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout_id, fragmentKamino)
                .commit();
    }

    private void animateImageViewHeight(boolean isExpanded) {
        final int dpToPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEIGHT_EXPAND_DP, getResources().getDisplayMetrics());
        final ViewGroup.LayoutParams layoutParams = imgKamino.getLayoutParams();

        final int targetHeight = isExpanded ? layoutParams.height + dpToPx : layoutParams.height - dpToPx;

        ValueAnimator animator = ValueAnimator.ofInt(layoutParams.height, targetHeight);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                layoutParams.height = (int) animation.getAnimatedValue();
                imgKamino.setLayoutParams(layoutParams);
            }
        });

        animator.start();
    }
}
