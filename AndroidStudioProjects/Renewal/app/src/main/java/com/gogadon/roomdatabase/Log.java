package com.gogadon.roomdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "logs")
public class Log {

    @PrimaryKey (autoGenerate = true)
    private int id_key;


    public Log(String date, String meal, String mood, String time, String location, String fooddrink, String thoughts, Boolean b, Boolean l, Boolean v) {
        this.date = date;
        this.meal = meal;
        this.mood = mood;
        this.time = time;
        this.location = location;
        this.fooddrink = fooddrink;
        this.thoughts = thoughts;
        this.b = b;
        this.l = l;
        this.v = v;
    }

    public int getId_key() {
        return id_key;
    }

    public void setId_key(int id_key) {
        this.id_key = id_key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFooddrink() {
        return fooddrink;
    }

    public void setFooddrink(String fooddrink) {
        this.fooddrink = fooddrink;
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }

    public Boolean getB() {
        return b;
    }

    public void setB(Boolean b) {
        this.b = b;
    }

    public Boolean getL() {
        return l;
    }

    public void setL(Boolean l) {
        this.l = l;
    }

    public Boolean getV() {
        return v;
    }

    public void setV(Boolean v) {
        this.v = v;
    }

    @ColumnInfo (name = "log_date")
    private String date;

    @ColumnInfo (name = "log_meal")
    private String meal;

    @ColumnInfo (name = "log_mood")
    private String mood;

    @ColumnInfo (name = "log_time")
    private String time;

    @ColumnInfo (name = "log_location")
    private String location;

    @ColumnInfo (name = "log_fooddrink")
    private String fooddrink;

    @ColumnInfo (name = "log_thoughts")
    private String thoughts;

    @ColumnInfo (name = "log_b")
    private Boolean b;

    @ColumnInfo (name = "log_l")
    private Boolean l;

    @ColumnInfo (name = "log_v")
    private Boolean v;







}
