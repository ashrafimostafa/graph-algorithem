package com.dev.mostafa.maximummatching.main;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dev.mostafa.maximummatching.R;
import com.dev.mostafa.maximummatching.customview.NodeCV;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawGraphFragment extends Fragment implements View.OnClickListener {

    //layout views
    RelativeLayout rootView;

    List<NodeCV> nodeCVList = new ArrayList<>();

    View view;

    int cnt = 0;

    private int _xDelta;
    private int _yDelta;

    public DrawGraphFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_draw_graph, container, false);
        init(view);



















        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        NodeCV nodeCV1 = new NodeCV(getContext());
//        nodeCV1.setNode("B" , 200 , 200);
//        nodeCVList.add(nodeCV1);
//        rootView.addView(nodeCV1);
//        nodeCV1.setOnClickListener(DrawGraphFragment.this);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action  = MotionEventCompat.getActionMasked(event);
                Log.i("graphh" , "here:" + action);
                switch (action){
                    case (MotionEvent.ACTION_DOWN):
                        cnt++;
                        NodeCV nodeCV = new NodeCV(getContext());
                        nodeCV.setNode(cnt + "" , event.getX() , event.getY());
                        nodeCVList.add(nodeCV);
                        rootView.addView(nodeCV);
                        nodeCV.setOnClickListener(DrawGraphFragment.this);

                        Log.i("graphh" , "node added " + cnt );
                        return false;
                    default:
                        return false;
                }
            }
        });

    }

    private void init(View view){
        rootView = view.findViewById(R.id.draw_graph_root);

    }


    @Override
    public void onClick(View v) {
        for (int i = 0; i <nodeCVList.size() ; i++) {
            if (v == nodeCVList.get(i)){
                Toast.makeText(getContext()
                        , "node click: " + nodeCVList.get(i).getNodeName()
                        , Toast.LENGTH_SHORT).show();
            }

        }

    }
}