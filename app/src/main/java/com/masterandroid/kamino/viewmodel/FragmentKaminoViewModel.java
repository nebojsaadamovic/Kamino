package com.masterandroid.kamino.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.masterandroid.kamino.data.api.Constants;
import com.masterandroid.kamino.data.api.StarWarsApiService;
import com.masterandroid.kamino.data.model.LikeRequest;
import com.masterandroid.kamino.data.model.Planet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentKaminoViewModel extends ViewModel {

    private final MutableLiveData<Planet> planetLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> likesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasLikedLiveData = new MutableLiveData<>(false);

    private final StarWarsApiService starWarsApiService;
    private boolean showData = true;

    public FragmentKaminoViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        starWarsApiService = retrofit.create(StarWarsApiService.class);
    }

    public LiveData<Planet> getPlanetLiveData() {
        return planetLiveData;
    }

    public LiveData<Integer> getLikesLiveData() {
        return likesLiveData;
    }

    public LiveData<Boolean> getHasLikedLiveData() {
        return hasLikedLiveData;
    }

    public void setShowData(boolean showData) {
        this.showData = showData;
        if (showData) {
            fetchPlanetData();
        } else {
            planetLiveData.setValue(null);
        }
    }

    public void likePlanet(int planetId) {
        if (hasLikedLiveData.getValue() != null && hasLikedLiveData.getValue()) {
            return;
        }

        LikeRequest likeRequest = new LikeRequest(planetId);
        Call<Void> call = starWarsApiService.likePlanet(planetId, likeRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    int currentLikes = likesLiveData.getValue() != null ? likesLiveData.getValue() : 0;
                    likesLiveData.setValue(currentLikes + 1);
                    hasLikedLiveData.setValue(true); // Set liked status
                } else {

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void fetchPlanetData() {
        // Replace with your planet ID
        int planetId = 10;
        Call<Planet> call = starWarsApiService.getKaminoPlanet(planetId);
        call.enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(Call<Planet> call, Response<Planet> response) {
                if (response.isSuccessful()) {
                    Planet planet = response.body();
                    if (planet != null) {
                        planetLiveData.setValue(planet);
                        likesLiveData.setValue(planet.getLikes()); // Set initial likes
                        hasLikedLiveData.setValue(planet.isLiked()); // Update if already liked
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<Planet> call, Throwable t) {
            }
        });
    }
}
