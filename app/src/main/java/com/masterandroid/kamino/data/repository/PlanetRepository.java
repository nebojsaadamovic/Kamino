package com.masterandroid.kamino.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;

import com.masterandroid.kamino.data.dao.PlanetDao;
import com.masterandroid.kamino.data.model.Planet;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PlanetRepository {
    private final PlanetDao planetDao;
    private final Executor executor;

    public PlanetRepository(PlanetDao planetDao) {
        this.planetDao = planetDao;
        this.executor = Executors.newSingleThreadExecutor();
    }


    public void insert(Planet planet) {
        executor.execute(() -> {
            planetDao.insert(planet);
        });
    }

    public void update(Planet planet) {
        executor.execute(() -> {
            planetDao.update(planet);
        });
    }


    public LiveData<List<Planet>> getAllPlanets() {
        return planetDao.getAllPlanets();
    }

    public LiveData<Planet> getPlanetById(Integer id) {
        return planetDao.getPlanetById(id);
    }
}
