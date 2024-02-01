package com.example.databaseroom;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

public class StudentDBInstance{
    private static StudentDataBase database;

    public static StudentDataBase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context,
                            StudentDataBase.class, "app_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }
}


