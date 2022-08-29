package com.mahabharata.shayaripro;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface maindao {
    @Insert
    public void addData(mainmodel mainmodel);

    @Query("select * from mainmodel")
    public List<mainmodel> getalldata();

    @Query("SELECT EXISTS (SELECT 1 FROM mainmodel WHERE id=:id)")
    public int isFavorite(String id);

    @Delete
    public void delete(mainmodel mainmodel);

}


