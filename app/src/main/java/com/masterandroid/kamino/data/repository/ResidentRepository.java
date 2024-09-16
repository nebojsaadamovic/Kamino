package com.masterandroid.kamino.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import com.masterandroid.kamino.data.dao.ResidentDao;
import com.masterandroid.kamino.data.model.Planet;
import com.masterandroid.kamino.data.model.Resident;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ResidentRepository {

    private final ResidentDao residentDao;
    private final Executor executor;

    public ResidentRepository(ResidentDao residentDao) {
        this.residentDao=residentDao;
        this.executor = Executors.newSingleThreadExecutor();
    }



    public void insert(Resident resident) {
        executor.execute(() -> {residentDao.insert(resident);});
    }

    public void update(Resident resident) {
        executor.execute(() -> {residentDao.update(resident);});
    }

    public LiveData<Resident> getResidentById(Integer residentId) {
        return residentDao.getResidentById(residentId);
    }

    public LiveData<List<Resident>> getResidentsByPlanet(Integer planetId) {
        return residentDao.getResidentsByPlanet(planetId);
    }

}
