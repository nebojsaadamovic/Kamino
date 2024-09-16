package com.masterandroid.kamino.data.dao;

import androidx.lifecycle.LiveData;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.masterandroid.kamino.data.model.Planet;

import java.util.List;

@Dao
public interface PlanetDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE) // Dodaj ovu strategiju
    void insert(Planet planet);

    @Update
    void update(Planet planet);

    @Query("SELECT * FROM planet ORDER BY name desc ")
    LiveData<List<Planet>> getAllPlanets();

    @Query("SELECT * FROM planet WHERE id = :id")
    LiveData<Planet> getPlanetById(Integer id);


}
