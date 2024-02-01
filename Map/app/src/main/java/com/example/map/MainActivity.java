package com.example.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.map.selector.ItemSelector;

public class MainActivity extends AppCompatActivity {

    MapDisplay mapDisplay = new MapDisplay();
    ItemSelector selector = new ItemSelector();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadDisplay();
        loadSelector();
        mapDisplay.setStruct(selector);
    }

    private void loadDisplay(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.map); //find frame

        if(frag==null)
        {
            fm.beginTransaction().add(R.id.map, mapDisplay).commit(); //assign fragment to a frame
        }
        else
        {
            fm.beginTransaction().replace(R.id.map, mapDisplay).commit();
        }
    }

    private void loadSelector(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.selector); //find frame

        if(frag==null)
        {
            fm.beginTransaction().add(R.id.selector, selector).commit(); //assign fragment to a frame
        }
        else
        {
            fm.beginTransaction().replace(R.id.selector, selector).commit();
        }
    }
}