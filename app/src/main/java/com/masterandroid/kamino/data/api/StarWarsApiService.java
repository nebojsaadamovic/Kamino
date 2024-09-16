package com.masterandroid.kamino.data.api;

import com.masterandroid.kamino.data.model.Planet;
import com.masterandroid.kamino.data.model.Resident;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface StarWarsApiService {


    @GET("planets/{id}")
    Call<Planet> getKaminoPlanet(@Path("id") int id);


    @POST("planets/{id}/like")
    Call<Planet> likePlanet(@Path("id") int planetId);

    @GET
    Call<Resident> getResidentDetails(@Url String url);

    @GET("residents/{id}")
    Call<Resident> getResident(@Path("id") int id);


    @GET("planets/10")
    Call<Planet> getKaminoPlanet();
}
