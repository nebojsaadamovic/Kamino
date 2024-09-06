package com.masterandroid.kamino.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.masterandroid.kamino.data.api.StarWarsApiService;
import com.masterandroid.kamino.data.model.Resident;
import com.masterandroid.kamino.data.model.Planet;
import com.masterandroid.kamino.data.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentResidentViewModel extends ViewModel {

    private final MutableLiveData<List<Resident>> residents = new MutableLiveData<>(new ArrayList<>());
    private final StarWarsApiService starWarsApiService;

    public FragmentResidentViewModel() {
        Retrofit retrofitClient = RetrofitClient.getClient();
        starWarsApiService = retrofitClient.create(StarWarsApiService.class);
        fetchResidents();
    }

    public LiveData<List<Resident>> getResidents() {
        return residents;
    }

    private void fetchResidents() {
        Call<Planet> call = starWarsApiService.getKaminoPlanet(10);
        call.enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(Call<Planet> call, Response<Planet> response) {
                if (response.isSuccessful()) {
                    Planet planet = response.body();
                    if (planet != null) {
                        List<String> residentUrls = planet.getResidents();
                        if (residentUrls != null && !residentUrls.isEmpty()) {
                            List<Resident> residentList = new ArrayList<>();
                            for (String url : residentUrls) {
                                starWarsApiService.getResidentDetails(url).enqueue(new Callback<Resident>() {
                                    @Override
                                    public void onResponse(Call<Resident> call, Response<Resident> response) {
                                        if (response.isSuccessful()) {
                                            Resident resident = response.body();
                                            if (resident != null) {
                                                residentList.add(resident);
                                                residents.postValue(residentList);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Resident> call, Throwable t) {
                                    }
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Planet> call, Throwable t) {
            }
        });
    }
}
