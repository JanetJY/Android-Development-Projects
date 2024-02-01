package com.example.contacts.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contacts.database.Contact;

import java.util.List;

@Dao
public interface ContactDAO {

    @Insert
    void insert(Contact... contact);

    @Update
    void update(Contact... contact);

    @Delete
    void delete(Contact... contact);

    @Query("SELECT * FROM contacts WHERE name = :pName")
    Contact getContactByNum(String pName);

    @Query("SELECT * FROM contacts ORDER BY name ASC")
    List<Contact> getAllContacts();
}
