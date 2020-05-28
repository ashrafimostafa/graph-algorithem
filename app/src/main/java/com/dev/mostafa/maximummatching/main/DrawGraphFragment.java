package com.dev.mostafa.maximummatching.main;

import android.Manifest;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.mostafa.maximummatching.R;
import com.dev.mostafa.maximummatching.customview.EdgeCV;
import com.dev.mostafa.maximummatching.customview.NodeCV;
import com.dev.mostafa.maximummatching.model.NodeDM;
import com.dev.mostafa.maximummatching.tool.Constant;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawGraphFragment extends Fragment implements View.OnClickListener  , RadioGroup.OnCheckedChangeListener {

    //layout views
    private RelativeLayout rootView , rootViewEdge;
    private View view;
    private RadioGroup modeRadio;
    private RadioButton nodeRadio, edgeRadio;

    /**
     * list of drawn node
     */
    private List<NodeDM> nodeDMList = new ArrayList<>();

    /**
     * list of drawn node views
     */
    private List<NodeCV> nodeCVList = new ArrayList<>();

    /**
     * list of drawn edge
     */
    private List<EdgeCV> edgeCVList = new ArrayList<>();

    private NodeCV selectedNode ;

    /**
     * counter for node capturing
     */
    private int cnt = 0;

    /**
     * declare working mode : node or edge
     */
    private int mode;

    boolean startSelected = false;
    boolean endSelected = false;

    float sx, sy, ex, ey;

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
        setInfo();


        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        if (mode == Constant.MODE_EDIT_EDGE) {
                            return true;
                        } else {
                            cnt++;
                            NodeCV nodeCV = new NodeCV(getContext());
                            nodeCV.setNode(cnt + "", event.getX(), event.getY());
                            nodeCVList.add(nodeCV);
                            rootView.addView(nodeCV);
                            nodeCV.setOnClickListener(DrawGraphFragment.this);
                            Log.i("graphh", "node added " + cnt);
                            return false;
                        }
                    default:
                        return false;
                }
            }
        });

    }

    /**
     * connect widget to xml file
     *
     * @param view root vew use for finding view
     */
    private void init(View view) {
        rootView = view.findViewById(R.id.draw_graph_root);
        rootViewEdge = view.findViewById(R.id.draw_graph_root_edge);
        modeRadio = view.findViewById(R.id.draw_graph_mode_radio);
        nodeRadio = view.findViewById(R.id.draw_graph_node_radio);
        edgeRadio = view.findViewById(R.id.draw_graph_edge_radio);

        modeRadio.setOnCheckedChangeListener(DrawGraphFragment.this);


    }

    /**
     * set info to widget in primary step
     */
    private void setInfo() {
        nodeRadio.setChecked(true);
        mode = Constant.MODE_EDIT_NODE;

    }


    /**
     * handle all click on widget
     *
     * @param v clicked view
     */
    @Override
    public void onClick(View v) {

        for (int i = 0; i < nodeCVList.size(); i++) {
            if (v == nodeCVList.get(i)) {
                if (mode == Constant.MODE_EDIT_NODE) {
                    selectedNode = nodeCVList.get(i);
                    showRemoveNodeBottomSheet();

                }else {
                    if (!startSelected) {
                        Toast.makeText(getContext(), "start selected", Toast.LENGTH_SHORT).show();
                        startSelected = true;
                        sx = nodeCVList.get(i).getNodeInfo().getX();
                        sy = nodeCVList.get(i).getNodeInfo().getY();
                    } else {
                        startSelected = false;
                        endSelected = false;
                        ex = nodeCVList.get(i).getNodeInfo().getX();
                        ey = nodeCVList.get(i).getNodeInfo().getY();
                        EdgeCV edgeCV = new EdgeCV(getContext(), sx, sy, ex, ey);
                        rootViewEdge.addView(edgeCV);
                        edgeCVList.add(edgeCV);
                    }

                }

            }

        }


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == modeRadio){
            modeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.draw_graph_node_radio) {
                        mode = Constant.MODE_EDIT_NODE;
                        Log.i("graphh", "mode: " + mode);
                    } else {
                        mode = Constant.MODE_EDIT_EDGE;
                        Log.i("graphh", "mode: " + mode);
                    }

                }
            });
        }

    }

    private void showRemoveNodeBottomSheet(){
        View view = getLayoutInflater().inflate(R.layout.sheet_remove_node , null);
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);
        dialog.show();


        TextView ok = view.findViewById(R.id.sheet_remove_node_ok);
        TextView cancel = view.findViewById(R.id.sheet_remove_node_cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootView.removeView(selectedNode);
                nodeCVList.remove(selectedNode);
                //todo remove it from nodeDMList and remove connected edge
                dialog.dismiss();



            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
