package com.example.contacts.display;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts.R;
import com.example.contacts.database.Contact;
import com.example.contacts.database.ContactDAO;
import com.example.contacts.database.DBInstance;
import com.example.contacts.MainActivityData;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    List<Contact> data;
    MainActivityData mainActivityDataViewModel;

    public Adapter(ContactDAO dataDAO, MainActivityData link){
        this.data = dataDAO.getAllContacts();
        mainActivityDataViewModel = link;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_view, parent, false);
        ViewHolder vHolder = new ViewHolder(view, mainActivityDataViewModel);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact singleData = data.get(position);
        holder.bind(singleData);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
