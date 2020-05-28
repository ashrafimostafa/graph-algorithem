package com.dev.mostafa.maximummatching.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dev.mostafa.maximummatching.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawGraphFragment extends Fragment {

    //layout views

    Button test;

    public DrawGraphFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_draw_graph, container, false);
        init(view);
        onClick();




        return view;
    }

    private void init(View view){


    }

    private void onClick(){

    }

}
