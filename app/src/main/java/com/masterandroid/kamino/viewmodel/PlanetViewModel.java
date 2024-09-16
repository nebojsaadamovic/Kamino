package com.masterandroid.kamino.viewmodel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


import com.masterandroid.kamino.data.api.AppDatabase;
import com.masterandroid.kamino.data.api.DatabaseClient;
import com.masterandroid.kamino.data.api.NetworkUtil;
import com.masterandroid.kamino.data.api.RetrofitClient;
import com.masterandroid.kamino.data.api.StarWarsApiService;
import com.masterandroid.kamino.data.model.Planet;
import com.masterandroid.kamino.data.repository.PlanetRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PlanetViewModel extends AndroidViewModel {
    private final StarWarsApiService starWarsApiService;
    private final PlanetRepository planetRepository;
    private final MutableLiveData<Boolean> showData = new MutableLiveData<>(false);
    private final MutableLiveData<Planet> planetDetails = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasLiked = new MutableLiveData<>(false);
    private final MutableLiveData<Integer> countLike = new MutableLiveData<>();
    private final MutableLiveData<String> image = new MutableLiveData<>();

    private Integer planetId;

    public PlanetViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = DatabaseClient.getDatabase(application);
        this.planetRepository = new PlanetRepository(database.planetDao());
        Retrofit retrofitClient = RetrofitClient.getClient();
        starWarsApiService = retrofitClient.create(StarWarsApiService.class);


    }

    public LiveData<Planet> getPlanetDetails() {
        return planetDetails;
    }

    public LiveData<Boolean> getShowData() {
        return showData;
    }

    public MutableLiveData<Integer> getCountLike() {
        return countLike;
    }

    public MutableLiveData<Boolean> getHasLiked() {
        return hasLiked;
    }


    public void checkInternetConnectionFetchLike(Integer planetId) {
        boolean isConnected = NetworkUtil.isNetworkAvailable(getApplication());
        Boolean currentShowData = showData.getValue();
        if (isConnected) {
            if(Boolean.FALSE.equals(currentShowData)){
                fetchPlanetKaminoDataFromApi();
                showData.postValue(true);
            }
            else{
                showData.setValue(false);
            }
        } else {
            if(Boolean.FALSE.equals(currentShowData)){
                fetchPlanetFromDatabase(planetId);
                showData.postValue(true);
            }
            else{
                showData.setValue(false);
            }
        }
    }


    public MutableLiveData<String> getImage() {
        return image;
    }

    public void fetchImageHeaderApi(){
        Call<Planet> callPlanet = starWarsApiService.getKaminoPlanet();
        callPlanet.enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(Call<Planet> call, Response<Planet> response) {
                if (response.isSuccessful()) {
                    Planet planet = response.body();
                    if (planet != null) {
                        image.postValue(planet.getImageUrl());
                    }
                } else {
                    Log.e("PlanetViewModel", "Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Planet> call, Throwable t) {
                Log.e("PlanetViewModel", "API Failure: " + t.getMessage());
            }
        });
    }

    public void fetchPlanetKaminoDataFromApi() {
        Call<Planet> callPlanet = starWarsApiService.getKaminoPlanet();
        callPlanet.enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(Call<Planet> call, Response<Planet> response) {
                if (response.isSuccessful()) {
                    Planet planet = response.body();
                    if (planet != null) {
                        String url = call.request().url().toString();
                        planetId = extractIdFromUrl(url);
                        planet.setId(planetId);
                        new Thread(() -> {
                            Planet existingPlanet = planetRepository.getPlanetById(planetId).getValue();
                            if (existingPlanet == null) {
                                planetRepository.insert(planet);
                            } else {
                                planetRepository.update(planet);
                            }
                            planetDetails.postValue(planet);
                        }).start();
                    }
                } else {
                    Log.e("PlanetViewModel", "Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Planet> call, Throwable t) {
                Log.e("PlanetViewModel", "API Failure: " + t.getMessage());
            }
        });
    }



    public void fetchPlanetFromDatabase(Integer planetId) {
        planetRepository.getPlanetById(planetId).observeForever(planet -> {
            if (planet != null) {
                new Handler(Looper.getMainLooper()).post(() -> planetDetails.setValue(planet));
            } else {
                Log.d("PlanetViewModel", "No planet found in database for ID: " + planetId);
            }
        });
    }


    private Integer extractIdFromUrl(String url) {
        try {
            String[] parts = url.split("/");
            return Integer.parseInt(parts[parts.length - 1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            Log.e("ResidentViewModel", "Error extracting ID from URL: " + url, e);
            return null;
        }
    }



    Integer likeCounterApi;
    Integer likesDb;
    Integer likeIncrease;
    public void likePlanet() {
        if (NetworkUtil.isNetworkAvailable(getApplication())) {
            Call<Planet> callPlanet = starWarsApiService.getKaminoPlanet();
            String url = callPlanet.request().url().toString();
            Integer idPlanetKamino = extractIdFromUrl(url);

            callPlanet.enqueue(new Callback<Planet>() {
                @Override
                public void onResponse(Call<Planet> call, Response<Planet> response) {
                    if (response.isSuccessful()) {
                        Planet planetApi = response.body();
                        if (planetApi != null) {
                            likeCounterApi = planetApi.getLikes();
                            likeIncrease = likeCounterApi + 1;
                            LiveData<Planet> planetDbLiveData = planetRepository.getPlanetById(idPlanetKamino);
                            planetDbLiveData.observeForever(new Observer<Planet>() {
                                @Override
                                public void onChanged(Planet planetDb) {
                                    if (planetDb != null) {
                                        if (planetDb.getLikes() <= planetApi.getLikes()) {
                                            planetDb.setLikes(likeIncrease);
                                                    planetRepository.update(planetDb);
                                            Call<Planet> likePlanetCall = starWarsApiService.likePlanet(idPlanetKamino);
                                            likePlanetCall.enqueue(new Callback<Planet>() {
                                                @Override
                                                public void onResponse(Call<Planet> call, Response<Planet> response) {
                                                    if (response.isSuccessful()) {
                                                        Planet updatedPlanet = response.body();
                                                        if (updatedPlanet != null) {
                                                            hasLiked.postValue(true);
                                                            countLike.postValue(likeIncrease);
                                                        } else {
                                                            hasLiked.postValue(false);

                                                        }
                                                    } else {
                                                        hasLiked.postValue(false);

                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Planet> call, Throwable t) {
                                                    hasLiked.postValue(false);
                                                }
                                            });
                                        } else if (planetDb.getLikes() > planetApi.getLikes()) {
                                            likesDb = planetDb.getLikes();
                                            Integer likesIncreaseDb = likesDb + 1;
                                            countLike.postValue(likesIncreaseDb);
                                        }
                                    } else {
                                        hasLiked.postValue(false);
                                    }
                                    planetDbLiveData.removeObserver(this);
                                }
                            });
                        } else {
                            hasLiked.postValue(false);
                        }
                    } else {
                        hasLiked.postValue(false);
                    }
                }

                @Override
                public void onFailure(Call<Planet> call, Throwable t) {
                    hasLiked.postValue(false);
                }
            });
        } else {

            String url = starWarsApiService.getKaminoPlanet().request().url().toString();
            System.out.println("nesooooo"+url);
            Integer idPlanetKamino = extractIdFromUrl(url);
// Kada je offline
            LiveData<Planet> planetDbOffline = planetRepository.getPlanetById(idPlanetKamino);

            planetDbOffline.observeForever(new Observer<Planet>() {
                @Override
                public void onChanged(Planet planet) {
                    if (planet != null) {
                        if (Boolean.FALSE.equals(hasLiked.getValue())) {

                            int increaseLikesDB = planet.getLikes() + 1;
                            planet.setLikes(increaseLikesDB);
                            new Thread(() -> {
                                planetRepository.update(planet);
                                hasLiked.postValue(true);
                                countLike.postValue(increaseLikesDB);
                                Log.d("HasLiked", "hasLiked " + hasLiked.getValue());
                            }).start();
                        }
                    } else {
                         hasLiked.postValue(false);
                    }
                    planetDbOffline.removeObserver(this);
                }
            });
        }
    }












}
