package com.example.map.selector;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.map.R;
import com.example.map.Structure;

public class ISAdapter extends RecyclerView.Adapter<ISViewHolder>  {
    StructureData data;
    public static int selected = -2;

    public ISAdapter(){
        data = StructureData.get();
    }

    @NonNull
    @Override
    public ISViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_view,parent,false);
        ISViewHolder vHolder = new ISViewHolder(view, this);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ISViewHolder holder, int position) {
        Structure singleData = data.get(position);
        holder.pic.setImageResource(singleData.getDrawableId());
        holder.label.setText(singleData.getLabel());
        holder.build = singleData;

        System.out.println("Actual Position: " + position);
        if(position == ISAdapter.selected) {

            holder.pic.setColorFilter(Color.argb(90, 0, 0, 20));
        }
        else
        {
            holder.pic.setColorFilter(Color.argb(0, 0, 0, 0));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
