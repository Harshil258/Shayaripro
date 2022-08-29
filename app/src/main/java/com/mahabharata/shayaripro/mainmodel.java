package com.mahabharata.shayaripro;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="mainmodel")
public class mainmodel {


    @PrimaryKey
    @NonNull
    public String id;
    @ColumnInfo(name = "category")
    public String category;
    @ColumnInfo(name = "shayari")
    public String shayari;
    public boolean isLike;


    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public mainmodel() {
    }

    public mainmodel(@NonNull String id, String category, String shayari, boolean isLike) {
        this.id = id;
        this.category = category;
        this.shayari = shayari;
        this.isLike = isLike;
    }

    public mainmodel(String category) {
        this.category = category;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShayari() {
        return shayari;
    }

    public void setShayari(String shayari) {
        this.shayari = shayari;
    }
}
