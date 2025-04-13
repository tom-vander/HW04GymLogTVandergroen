package com.example.hw04gymlogtvandergroen.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.hw04gymlogtvandergroen.Database.entities.GymLog;

import java.util.List;

@Dao
public interface GymLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GymLog gymLog);

    @Query("Select * from " + GymLogDatabase.gymLogTable + " order by date DESC")
    List<GymLog> getAllRecords();

    @Query("Select * from " + GymLogDatabase.gymLogTable + " WHERE userId = :loggedInUserId order by date DESC")
    List<GymLog> getRecordsByUserId(int loggedInUserId);
}
