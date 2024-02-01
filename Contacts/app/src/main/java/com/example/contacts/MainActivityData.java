package com.example.contacts;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.contacts.database.Contact;

public class MainActivityData extends ViewModel {

    public MutableLiveData<Integer> fragmentCoordinate;
    public MutableLiveData<Contact> toUpdate;

    public MainActivityData(){
        fragmentCoordinate = new MutableLiveData<Integer>();
        toUpdate = new MutableLiveData<Contact>();

        fragmentCoordinate.setValue(0);
        toUpdate.setValue(null);
    }

    public int getFragValue(){
        return fragmentCoordinate.getValue();
    }

    public void setFragValue(int value){
        fragmentCoordinate.setValue(value);
    }

    public Contact getToUpdate(){
        return toUpdate.getValue();
    }

    public void setToUpdate(Contact c){
        toUpdate.setValue(c);
    }
}
