package com.example.contacts.database;

import android.content.Context;

import androidx.room.Room;

import com.example.contacts.database.Database;

public class DBInstance {
    private static Database database;

    public static Database getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context,
                            Database.class, "app_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }
}
