package com.gogadon.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LogDAO {


    @Query("SELECT * FROM logs")
    List<Log> getall();

    @Query("SELECT * FROM logs Where log_date = :date")
    List<Log> getallfordate(String date);

    @Insert
    void insert(Log l);

    @Update
    void update(Log l);

    @Delete
    void delete(Log l);


}
