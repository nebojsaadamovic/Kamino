package com.masterandroid.kamino.data.api;

import com.masterandroid.kamino.data.model.LikeRequest;
import com.masterandroid.kamino.data.model.LikeResponse;
import com.masterandroid.kamino.data.model.Planet;
import com.masterandroid.kamino.data.model.Resident;

//import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface StarWarsApiService {


    @GET("planets/{id}")
    Call<Planet> getKaminoPlanet(@Path("id") int id);

    @POST("planets/10/like")
    Call<LikeResponse> likeKaminoPlanet(@Body LikeRequest body);

    @GET
    Call<Resident> getResidentDetails(@Url String url);
}
