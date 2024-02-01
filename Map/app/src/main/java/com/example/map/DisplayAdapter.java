package com.example.map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.map.selector.ISViewHolder;
import com.example.map.selector.StructureData;

import java.util.Map;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayVH> {
    private MapData data;

    public DisplayAdapter(){
        data = MapData.get();
    }

    @NonNull
    @Override
    public DisplayVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.map_element,parent,false);
        DisplayVH vHolder = new DisplayVH(view, parent, this, data);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayVH holder, int position) {
        int row = position % MapData.HEIGHT;
        int col = position / MapData.HEIGHT;

        MapElement singleData = data.get(row, col);

        holder.data = singleData;
        holder.NE.setImageResource(singleData.getNorthEast());
        holder.NW.setImageResource(singleData.getNorthWest());
        holder.SE.setImageResource(singleData.getSouthEast());
        holder.SW.setImageResource(singleData.getSouthWest());
        Structure s = singleData.getStructure();
        if(s != null)
        {
            System.out.println("Structure is here at: (" + row + ", " + col + ") ");
            holder.structure.setVisibility(View.VISIBLE);
            holder.structure.setImageResource(s.getDrawableId());
        }
        else
        {
            holder.structure.setVisibility(View.INVISIBLE);
        }
        //holder.bind(singleData);
    }

    @Override
    public int getItemCount() {
        return (MapData.WIDTH * MapData.HEIGHT);
    }
}