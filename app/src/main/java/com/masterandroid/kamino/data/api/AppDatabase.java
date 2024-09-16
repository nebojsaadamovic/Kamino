package com.masterandroid.kamino.data.api;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.masterandroid.kamino.data.model.Planet;
import com.masterandroid.kamino.data.model.Resident;
import com.masterandroid.kamino.data.dao.PlanetDao;
import com.masterandroid.kamino.data.dao.ResidentDao;

@Database(entities = {Planet.class, Resident.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
        public abstract PlanetDao planetDao();
        public abstract ResidentDao residentDao();
}
