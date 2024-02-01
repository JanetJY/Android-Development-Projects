package com.example.map.selector;

import android.graphics.Color;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.map.R;
import com.example.map.Structure;

public class ISViewHolder extends RecyclerView.ViewHolder {

    public TextView label;
    public ImageView pic;

    public Structure build;
    public ISViewHolder(@NonNull View itemView, RecyclerView.Adapter adapter) {
        super(itemView);
        pic = itemView.findViewById(R.id.structure);
        label = itemView.findViewById(R.id.label);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemSelector.setSelected(build);
                System.out.println("\n\n\n\n\n\n\n" + getBindingAdapterPosition());
                ISAdapter.selected = getBindingAdapterPosition();
                adapter.notifyDataSetChanged();
            }
        });
    }

}
