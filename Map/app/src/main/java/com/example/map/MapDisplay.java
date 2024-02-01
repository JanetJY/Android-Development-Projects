package com.example.map;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.map.selector.ISAdapter;
import com.example.map.selector.ItemSelector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapDisplay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapDisplay extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static ItemSelector struct;

    public MapDisplay() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapDisplay.
     */
    // TODO: Rename and change types and number of parameters
    public static MapDisplay newInstance(String param1, String param2) {
        MapDisplay fragment = new MapDisplay();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View pView = inflater.inflate(R.layout.fragment_map_display, container, false);
        RecyclerView recycler = pView.findViewById(R.id.recycler);
        Button button = pView.findViewById(R.id.button);
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), MapData.HEIGHT,
                GridLayoutManager.HORIZONTAL,
                false));

        DisplayAdapter adapter = new DisplayAdapter();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapData m = MapData.get();
                m.regenerate();
                adapter.notifyItemRangeChanged(0, MapData.WIDTH * MapData.HEIGHT);
            }
        });

        recycler.setAdapter(adapter);

        return pView;
    }

    public void setStruct(ItemSelector s)
    {
        struct = s;
    }

    public static Structure getSetStruct()
    {
        return struct.getSelected();
    }
}