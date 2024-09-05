package com.masterandroid.kamino.activitykamino;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.masterandroid.kamino.R;
import com.masterandroid.kamino.data.api.RetrofitClient;
import com.masterandroid.kamino.data.api.StarWarsApiService;
import com.masterandroid.kamino.data.model.Planet;
import com.masterandroid.kamino.data.model.Resident;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentResident extends Fragment {
    private StarWarsApiService starWarsApiService;
    private List<Resident> residents = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdaptorResident myAdaptorResident;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resident, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_id);
        Retrofit retrofitClient = RetrofitClient.getClient();
        if (retrofitClient == null) {
            Log.e("FragmentResident", "Retrofit client is null");
        }
        starWarsApiService = retrofitClient.create(StarWarsApiService.class);
        if (starWarsApiService == null) {
            Log.e("FragmentResident", "ApiService is null");
        }
        getResidentsList();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdaptorResident = new MyAdaptorResident(getContext(),residents);
        recyclerView.setAdapter(myAdaptorResident);
        return view;
    }

    private void getResidentsList() {
        Call<Planet> call = starWarsApiService.getKaminoPlanet(10);
        call.enqueue(new Callback<Planet>() {
            @Override
            public void onResponse(@NonNull Call<Planet> call, @NonNull Response<Planet> response) {
                if (response.isSuccessful()) {
                    Planet planet = response.body();
                    if (planet != null) {
                        List<String> residentUrls = planet.getResidents();
                        if (residentUrls != null && !residentUrls.isEmpty()) {
                            for (String url : residentUrls) {
                                starWarsApiService.getResidentDetails(url).enqueue(new Callback<Resident>() {
                                    @Override
                                    public void onResponse(@NonNull Call<Resident> call, @NonNull Response<Resident> response) {
                                        if (response.isSuccessful()) {
                                            Resident resident = response.body();
                                            if (resident != null) {
                                                residents.add(resident);
                                                Log.d("FragmentResident", "Resident added: " + resident.getName());
                                                myAdaptorResident.notifyDataSetChanged();
                                            }
                                        } else {
                                            Log.d("FragmentResident", "Response not successful for resident: " + response.code());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Resident> call, Throwable t) {
                                        Log.e("FragmentResident", "API call failure for resident", t);
                                    }
                                });
                            }
                        } else {
                            Log.d("FragmentResident", "No resident URLs found");
                        }
                    } else {
                        Log.d("FragmentResident", "Planet is null");
                    }
                } else {
                    Log.d("FragmentResident", "Response not successful: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Planet> call, Throwable t) {
                Log.e("FragmentResident", "API call failure", t);
            }
        });
    }
}
