package com.gogadon.roomdatabase;

import android.content.Context;

import androidx.cardview.widget.CardView;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Log.class, version = 1)
public abstract class DatabaseClass extends RoomDatabase {


    private static DatabaseClass singleinstance;
    public abstract  LogDAO logDAO();
    public static synchronized  DatabaseClass getInstance(Context context){


        //Create or retrive the single instance depending whether or not it has been created before

        if(singleinstance==null){

            singleinstance = Room.databaseBuilder(context.getApplicationContext(), DatabaseClass.class, "log_database").fallbackToDestructiveMigration().build();
        }

        return singleinstance;

    }


}
