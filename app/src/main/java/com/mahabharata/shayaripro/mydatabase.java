package com.mahabharata.shayaripro;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={mainmodel.class},version = 1)
abstract class mydatabase extends RoomDatabase {

    public abstract maindao maindao();

}
