package com.example.databaseroom;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class}, version = 1)
public abstract class StudentDataBase extends RoomDatabase {
    public abstract StudentDAO studentDAO();
}

