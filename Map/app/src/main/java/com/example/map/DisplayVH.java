package com.example.map;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.map.selector.ItemSelector;

import java.util.Map;

public class DisplayVH extends RecyclerView.ViewHolder {

    public ImageView NE;
    public ImageView SE;
    public ImageView SW;
    public ImageView NW;
    public ImageView structure;
    public MapElement data;

    public DisplayVH(@NonNull View itemView, ViewGroup parent, RecyclerView.Adapter adapter, MapData m){
        super(itemView);

        //data = m;
        int size = parent.getMeasuredHeight() / MapData.HEIGHT + 1;
        ViewGroup.LayoutParams lp = itemView.getLayoutParams();
        lp.width = size;
        lp.height = size;

        NE = itemView.findViewById(R.id.NE);
        SE = itemView.findViewById(R.id.SE);
        SW = itemView.findViewById(R.id.SW);
        NW = itemView.findViewById(R.id.NW);
        structure = itemView.findViewById(R.id.structure);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Structure s = MapDisplay.getSetStruct();
                int position = getAbsoluteAdapterPosition();

                if(s != null && data.getStructure() == null && data.isBuildable())
                {

                    int row = position % MapData.HEIGHT;
                    int col = position / MapData.HEIGHT;
                    System.out.println("We are assigning to map: (" + row + ", " + col + ") ");
                    data.setStructure(s);
                    adapter.notifyItemChanged(position);
                }
                else if(data.getStructure() != null)
                {
                    data.setStructure(null);
                    adapter.notifyItemChanged(position);
                }
            }
        });
    }

}
