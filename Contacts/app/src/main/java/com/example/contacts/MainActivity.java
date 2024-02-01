package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.example.contacts.display.ContactList;

public class MainActivity extends AppCompatActivity {


    ContactList showContacts = new ContactList();
    AddProfile createContact = new AddProfile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadDisplay();

        MainActivityData mainActivityDVM = new ViewModelProvider(this).get(MainActivityData.class);
        mainActivityDVM.fragmentCoordinate.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(mainActivityDVM.getFragValue() == 0)
                {
                    loadDisplay();
                }
                else if(mainActivityDVM.getFragValue() == 1)
                {
                    loadCreate();
                }
            }
        });
    }


    private void loadDisplay(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.f_container); //find frame

        if(frag==null)
        {
            fm.beginTransaction().add(R.id.f_container, showContacts).commit(); //assign fragment to a frame
        }
        else
        {
            fm.beginTransaction().replace(R.id.f_container, showContacts).commit();
        }
    }

    private void loadCreate(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.f_container); //find frame

        if(frag==null)
        {
            fm.beginTransaction().add(R.id.f_container, createContact).commit(); //assign fragment to a frame
        }
        else
        {
            fm.beginTransaction().replace(R.id.f_container, createContact).commit();
        }
    }


}