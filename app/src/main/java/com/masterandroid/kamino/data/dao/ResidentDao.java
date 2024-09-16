package com.masterandroid.kamino.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.masterandroid.kamino.data.model.Resident;

import java.util.List;

@Dao
public interface ResidentDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Resident resident);

    @Update
    void update(Resident resident);

    @Query("SELECT * FROM resident WHERE id = :id LIMIT 1")
    LiveData<Resident> getResidentById(int id);

    @Query("SELECT * FROM resident WHERE planet_id = :planetId")
    LiveData<List<Resident>> getResidentsByPlanet(Integer planetId);

}
