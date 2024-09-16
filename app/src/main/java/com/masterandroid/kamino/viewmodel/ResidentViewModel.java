package com.masterandroid.kamino.viewmodel;

import android.app.Application;
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
import com.masterandroid.kamino.data.model.Resident;
import com.masterandroid.kamino.data.repository.PlanetRepository;
import com.masterandroid.kamino.data.repository.ResidentRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ResidentViewModel extends AndroidViewModel {

    private final StarWarsApiService starWarsApiService;

    private final ResidentRepository residentRepository;
    private final PlanetRepository planetRepository;
    private final MutableLiveData<List<Resident>> allResidentsByPlanet = new MutableLiveData<>();
    private final MutableLiveData<Integer> planetId = new MutableLiveData<>();
    private final MutableLiveData<Resident> residentLiveData = new MutableLiveData<>();


    public ResidentViewModel(@NonNull Application application) {
        super(application);


        AppDatabase database = DatabaseClient.getDatabase(application);
        this.residentRepository = new ResidentRepository(database.residentDao());
        this.planetRepository = new PlanetRepository(database.planetDao());

        Retrofit retrofitClient = RetrofitClient.getClient();
        starWarsApiService = retrofitClient.create(StarWarsApiService.class);

        planetId.observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer planetId) {
                if (planetId != null) {
                    checkInternetConnectionAndFetchData(planetId);
                }
            }
        });




    }

    public LiveData<List<Resident>> getAllResidentsByPlanet() {
        return allResidentsByPlanet;
    }

    public void setPlanetId(Integer planetId) {
        this.planetId.setValue(planetId);
    }


    private void checkInternetConnectionAndFetchData(Integer planetId) {
        boolean isConnected = NetworkUtil.isNetworkAvailable(getApplication());
        if (isConnected) {
            fetchAllResidentsByPlanetFromApi(planetId);
        } else {
            fetchAllResidentsByPlanetFromDatabase(planetId);
        }
    }



    private void fetchAllResidentsByPlanetFromApi(Integer planetId) {
        Call<Planet> planetCall = starWarsApiService.getKaminoPlanet(planetId);
        planetCall.enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(Call<Planet> call, Response<Planet> response) {
                if (response.isSuccessful()) {
                    Planet planet = response.body();
                    System.out.println(planet);
                    if (planet != null) {
                        List<String> residentUrls = planet.getResidents();
                        if (residentUrls != null) {
                            Set<String> uniqueResidentUrls = new HashSet<>(residentUrls);
                            List<Resident> residentList = new ArrayList<>();

                            for (String url : uniqueResidentUrls) {
                                Integer idResident = extractIdFromUrl(url);
                                Call<Resident> callResident = starWarsApiService.getResidentDetails(url);
                                callResident.enqueue(new Callback<Resident>() {
                                    @Override
                                    public void onResponse(Call<Resident> call, Response<Resident> response) {
                                        if (response.isSuccessful()) {
                                            Resident resident = response.body();
                                            if (resident != null) {
                                                resident.setPlanetId(planetId);  // Postavi planetId pre unosa
                                                resident.setId(idResident);
                                                    Resident existingResident = residentRepository.getResidentById(idResident).getValue();
                                                    if (existingResident == null) {
                                                        residentRepository.insert(resident);
                                                        residentList.add(resident);
                                                        allResidentsByPlanet.postValue(residentList);
                                                    } else {
                                                        Log.d("ResidentViewModel", "Stanovnik alredy exust in database kamino_db: " + existingResident.getName());
                                                    }
                                            } else {
                                                Log.e("ResidentViewModel", "Resideent objectt: " + url);
                                            }
                                        } else {
                                            Log.e("ResidentViewModel", "Failed to fetch resiiident details for urlll: " + url);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Resident> call, Throwable t) {
                                        Log.e("ResidentViewModel", "Failed to fetch resident details", t);
                                    }
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Planet> call, Throwable t) {
                Log.e("ResidentViewModel", "Failed to fetch planet", t);
            }
        });
    }




    private void fetchAllResidentsByPlanetFromDatabase(Integer planetId) {
        LiveData<List<Resident>> residentsLiveData = residentRepository.getResidentsByPlanet(planetId);
        residentsLiveData.observeForever(new Observer<List<Resident>>() {
            @Override
            public void onChanged(List<Resident> residents) {
                if (residents != null) {
                    for (Resident resident : residents) {
                        Log.d("listaRezidenata", "Stanovnik: " + resident.toString());
                    }
                    allResidentsByPlanet.postValue(residents);
                }
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

    public void checkInternetConnectionResidentDetails(Integer id) {
        boolean isConnected = NetworkUtil.isNetworkAvailable(getApplication());
        if (isConnected) {
            fetchResidentDetailsFromApi(id);
        } else {
            fetchResidentDetailsFromDatabase(id);
        }
    }


    public MutableLiveData<Resident> getResidentLiveData(Integer id) {
        return residentLiveData;
    }

    public void fetchResidentDetailsFromApi(Integer id) {
        Call<Resident> callResident = starWarsApiService.getResident(id);
        callResident.enqueue(new Callback<Resident>() {
            @Override
            public void onResponse(Call<Resident> call, Response<Resident> response) {
                if (response.isSuccessful()) {
                    Resident resident = response.body();
                    if (resident != null) {
                        new Thread(() -> {
                            Resident existingResident = residentRepository.getResidentById(id).getValue();
                            if (existingResident == null) {
                                residentRepository.insert(resident);
                            } else {
                                residentRepository.update(resident);  // You should implement the update method in your repository
                            }
                            residentLiveData.postValue(resident);
                        }).start();
                    }
                }
            }

            @Override
            public void onFailure(Call<Resident> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void fetchResidentDetailsFromDatabase(Integer id) {
        LiveData<Resident> residentLiveDataFromDb = residentRepository.getResidentById(id);
        residentLiveDataFromDb.observeForever(new Observer<Resident>() {
            @Override
            public void onChanged(Resident resident) {
                if (resident != null) {
                    residentLiveData.postValue(resident);
                } else {
                    Log.d("ResidentViewModel", "No resident found in database with id: " + id);
                }
            }
        });
    }

}
