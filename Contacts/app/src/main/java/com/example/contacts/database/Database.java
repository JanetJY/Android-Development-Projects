package com.example.contacts.database;

import androidx.room.RoomDatabase;

import com.example.contacts.database.Contact;
import com.example.contacts.database.ContactDAO;

@androidx.room.Database(entities = {Contact.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract ContactDAO contactDAO();
}
