package com.example.contacts.database;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.File;

@Entity(tableName = "contacts")
public class Contact {

    @PrimaryKey
    @NonNull
    public String name;

    public String email;

    public String phoneNo;

    public byte[] imageData;

    //public Bitmap photo;
}
