package com.dev.mostafa.maximummatching.main;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.mostafa.maximummatching.R;
import com.dev.mostafa.maximummatching.algorithm.matching.EdmondBlossomMaxMatch;
import com.dev.mostafa.maximummatching.algorithm.matching.MNode;
import com.dev.mostafa.maximummatching.algorithm.prim.Graph;
import com.dev.mostafa.maximummatching.algorithm.prim.MST;
import com.dev.mostafa.maximummatching.customview.EdgeCV;
import com.dev.mostafa.maximummatching.customview.NodeCV;
import com.dev.mostafa.maximummatching.database.DataBaseHelper;
import com.dev.mostafa.maximummatching.model.AlgorithmDM;
import com.dev.mostafa.maximummatching.model.EdgeDM;
import com.dev.mostafa.maximummatching.model.GraphDM;
import com.dev.mostafa.maximummatching.model.NodeDM;
import com.dev.mostafa.maximummatching.tool.Constant;
import com.google.android.material.bottomsheet.BottomSheetDialog;


import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawGraphFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    //layout views
    private RelativeLayout rootView, rootViewEdge;
    private View view;
    private RadioGroup modeRadio;
    private RadioButton nodeRadio, edgeRadio;
    private ImageView graphList, apply, save, remove, setting, help;

    private DataBaseHelper dataBaseHelper;

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

    private NodeCV selectedNode;

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
        dataBaseHelper = new DataBaseHelper(getContext());
        graphList = view.findViewById(R.id.draw_graph_list);
        apply = view.findViewById(R.id.draw_graph_apply);
        save = view.findViewById(R.id.draw_graph_save);
        setting = view.findViewById(R.id.draw_graph_setting);
        remove = view.findViewById(R.id.draw_graph_remove);
        help = view.findViewById(R.id.draw_graph_question);
        graphList.setOnClickListener(DrawGraphFragment.this);
        apply.setOnClickListener(DrawGraphFragment.this);
        save.setOnClickListener(DrawGraphFragment.this);
        setting.setOnClickListener(DrawGraphFragment.this);
        remove.setOnClickListener(DrawGraphFragment.this);
        help.setOnClickListener(DrawGraphFragment.this);

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
        if (v == save) {
            if (nodeCVList.size() < 1) {
                Toast.makeText(getContext(), "At least enter one node!", Toast.LENGTH_SHORT).show();
            } else {
                showSaveGraphBottomSheet();
            }

        } else if (v == remove) {
            if (nodeCVList.size() < 1) {
                Toast.makeText(getContext(), "Nothing to remove!", Toast.LENGTH_SHORT).show();
            } else {
                showRemoveBottomSheet();
            }

        } else if (v == graphList) {
            showGraphListBottomSheet();
        } else if (v == apply) {
            if (nodeCVList.size() < 1) {
                Toast.makeText(getContext(), "At least enter one node!", Toast.LENGTH_SHORT).show();
            } else {
                showAlgorithmListBottomSheet();
            }
        } else if (v == help) {
            showHelpDialog();
        } else {
            for (int i = 0; i < nodeCVList.size(); i++) {
                if (v == nodeCVList.get(i)) {
                    if (mode == Constant.MODE_EDIT_NODE) {
                        selectedNode = nodeCVList.get(i);
                        showRemoveNodeBottomSheet();

                    } else {
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
                            showGetEdgeWeightDialog();
                        }

                    }

                }

            }
        }


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == modeRadio) {
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

    private void showRemoveNodeBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.sheet_remove_node, null);
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

    private void showSaveGraphBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.sheet_add_graph, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);
        dialog.show();


        final EditText name = view.findViewById(R.id.sheet_add_graph_name);
        TextView ok = view.findViewById(R.id.sheet_add_graph_ok);
        TextView cancel = view.findViewById(R.id.sheet_add_graph_cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.getText().toString().length() < 1) {
                    name.setError("enter graph name");
                } else {
                    int graphId = (int) dataBaseHelper.addGraph(name.getText().toString());
                    for (int i = 0; i < nodeCVList.size(); i++) {
                        NodeDM nodeDM = nodeCVList.get(i).getNodeInfo();
                        int id = (int) dataBaseHelper.AddNode(nodeDM.getName(), graphId
                                , nodeDM.getX(), nodeDM.getY());
                        nodeCVList.get(i).setId(id);
                    }

                    for (int i = 0; i < edgeCVList.size(); i++) {
                        int startNode = 0;
                        int endNode = 0;
                        EdgeDM edgeDM = edgeCVList.get(i).getEdgeInfo();
                        float[] position = edgeCVList.get(i).getEdgePosition();

                        for (int j = 0; j < nodeCVList.size(); j++) {
                            if (nodeCVList.get(j).getNodeInfo().getX()
                                    == position[0] &&
                                    nodeCVList.get(j).getNodeInfo().getY()
                                            == position[1]) {
                                startNode = nodeCVList.get(j).getId();
                            }
                            if (nodeCVList.get(j).getNodeInfo().getX()
                                    == position[2] &&
                                    nodeCVList.get(j).getNodeInfo().getY()
                                            == position[3]) {
                                endNode = nodeCVList.get(j).getId();
                            }
                        }

                        dataBaseHelper.AddEdge(edgeDM.getName(), graphId
                                , startNode, endNode
                                , edgeDM.getWeight());
                    }
                    dialog.dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void showRemoveBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.sheet_remove_page, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);
        dialog.show();


        TextView ok = view.findViewById(R.id.sheet_remove_page_ok);
        TextView cancel = view.findViewById(R.id.sheet_remove_page_cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootView.removeAllViews();
                rootViewEdge.removeAllViews();
                nodeCVList.clear();
                edgeCVList.clear();
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

    private void showGraphListBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.sheet_graph_list, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);
        dialog.show();

        TextView error = view.findViewById(R.id.sheet_graph_list_error);
        ListView listView = view.findViewById(R.id.sheet_graph_list_list);
        List<GraphDM> graphDMS = new ArrayList<>();
        List<String> stringArrayList = new ArrayList<>();
        graphDMS = dataBaseHelper.getAllGraph();
        if (graphDMS.size() == 0) {
            listView.setVisibility(View.GONE);
            error.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            error.setVisibility(View.GONE);
        }
        for (int i = 0; i < graphDMS.size(); i++) {
            stringArrayList.add(graphDMS.get(i).getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext()
                , android.R.layout.simple_list_item_1
                , stringArrayList.toArray());
        listView.setAdapter(arrayAdapter);

        final List<GraphDM> temporary = graphDMS;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadGraph(temporary.get(position));
                dialog.dismiss();
            }
        });
    }

    private void loadGraph(GraphDM selectedGraph) {
        rootView.removeAllViews();
        rootViewEdge.removeAllViews();
        nodeCVList.clear();
        edgeCVList.clear();
        List<NodeDM> nodeDMList =
                dataBaseHelper.getNodesOfGraph(selectedGraph.getId());

        List<EdgeDM> edgeDMList =
                dataBaseHelper.getEdgesOfGraph(selectedGraph.getId());

        for (int i = 0; i < nodeDMList.size(); i++) {
            cnt++;
            NodeCV nodeCV = new NodeCV(getContext());
            nodeCV.setNode(nodeDMList.get(i).getName()
                    , nodeDMList.get(i).getX()
                    , nodeDMList.get(i).getY());
            nodeCVList.add(nodeCV);
            rootView.addView(nodeCV);
            nodeCV.setOnClickListener(DrawGraphFragment.this);
        }

        for (int i = 0; i < edgeDMList.size(); i++) {
            NodeDM startNode = dataBaseHelper.getNodeById(edgeDMList.get(i).getStartNode());
            NodeDM endNode = dataBaseHelper.getNodeById(edgeDMList.get(i).getEndNode());
            EdgeCV edgeCV = new EdgeCV(getContext()
                    , startNode.getX()
                    , startNode.getY()
                    , endNode.getX()
                    , endNode.getY()
                    , edgeDMList.get(i).getWeight());
            rootViewEdge.addView(edgeCV);
            edgeCVList.add(edgeCV);
        }

    }

    private void showAlgorithmListBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.sheet_algorithm_list, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);
        dialog.show();

        ListView listView = view.findViewById(R.id.sheet_algorithm_list_list);
        List<AlgorithmDM> algorithmDMS = new ArrayList<>();
        List<String> stringArrayList = new ArrayList<>();
        algorithmDMS = dataBaseHelper.getAllAlgorithm();

        for (int i = 0; i < algorithmDMS.size(); i++) {
            stringArrayList.add(algorithmDMS.get(i).getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext()
                , android.R.layout.simple_list_item_1
                , stringArrayList.toArray());
        listView.setAdapter(arrayAdapter);

        final List<AlgorithmDM> temporary = algorithmDMS;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        applyAlgorithmMM(temporary.get(position));
                        dialog.dismiss();
                        break;
                    case 1:
                        applyAlgorithmPrime();
                        dialog.dismiss();
                        break;
                    case 2:
                        applyAlgorithmKruskal();
                        dialog.dismiss();
                        break;
                }

            }
        });

    }

    private void applyAlgorithmPrime() {

        int nodeCnt, edgeCnt;
        nodeCnt = nodeCVList.size();
        edgeCnt = edgeCVList.size();


        Map<Integer, NodeDM> nodeDMMap = new HashMap<>();

        int[][] edgeArray = new int[edgeCnt][3];


        //map each node to a number
        for (int i = 0; i < nodeCnt; i++) {
            NodeDM nodeDM = nodeCVList.get(i).getNodeInfo();
            nodeDMMap.put(i, nodeDM);
        }

        //set array of edge according to map of node
        for (int j = 0; j < edgeCnt; j++) {
            int start = -1;
            int end = -1;
            float[] position = new float[5];

            position = edgeCVList.get(j).getEdgePositionWithWeight();

            for (int i = 0; i < nodeCnt; i++) {
                if (nodeDMMap.get(i).getX() == position[0] &&
                        nodeDMMap.get(i).getY() == position[1]) {
                    start = i;
                }
                if (nodeDMMap.get(i).getX() == position[2] &&
                        nodeDMMap.get(i).getY() == position[3]) {
                    end = i;
                }
            }

            edgeArray[j][0] = start;
            edgeArray[j][1] = end;
            edgeArray[j][2] = (int) position[4];

        }

        int graph[][] = new int[nodeCnt][nodeCnt];

        Graph graph1 = new Graph(edgeCnt);
        for (int i = 0; i < edgeCnt; i++) {
            graph1.addEdge(edgeArray[i][0], edgeArray[i][1], edgeArray[i][2]);
        }

        graph = graph1.getMatrix();

        MST mst = new MST(nodeCnt);
        int result[] = mst.primMST(graph);

        for (int i = 1; i < nodeCnt; i++) {
            EdgeCV edgeCV = new EdgeCV(getContext()
                    , nodeDMMap.get(result[i]).getX()
                    , nodeDMMap.get(result[i]).getY()
                    , nodeDMMap.get(i).getX()
                    , nodeDMMap.get(i).getY()
            );
            rootViewEdge.addView(edgeCV);
        }
    }

    private void applyAlgorithmKruskal() {

        int nodeCnt, edgeCnt;
        nodeCnt = nodeCVList.size();
        edgeCnt = edgeCVList.size();

        com.dev.mostafa.maximummatching.algorithm.kruskal.Graph graph1 =
                new com.dev.mostafa.maximummatching.algorithm.kruskal.Graph(
                        nodeCnt , edgeCnt);

        Map<Integer, NodeDM> nodeDMMap = new HashMap<>();

        int[][] edgeArray = new int[edgeCnt][3];


        //map each node to a number
        for (int i = 0; i < nodeCnt; i++) {
            NodeDM nodeDM = nodeCVList.get(i).getNodeInfo();
            nodeDMMap.put(i, nodeDM);
        }

        //set array of edge according to map of node
        for (int j = 0; j < edgeCnt; j++) {
            int start = -1;
            int end = -1;
            float[] position = new float[5];

            position = edgeCVList.get(j).getEdgePositionWithWeight();

            for (int i = 0; i < nodeCnt; i++) {
                if (nodeDMMap.get(i).getX() == position[0] &&
                        nodeDMMap.get(i).getY() == position[1]) {
                    start = i;
                }
                if (nodeDMMap.get(i).getX() == position[2] &&
                        nodeDMMap.get(i).getY() == position[3]) {
                    end = i;
                }
            }

            edgeArray[j][0] = start;
            edgeArray[j][1] = end;
            edgeArray[j][2] = (int) position[4];

        }

        for (int i = 0; i <edgeCnt ; i++) {
            graph1.edge[i].src = edgeArray[i][0];
            graph1.edge[i].dest = edgeArray[i][1];
            graph1.edge[i].weight = edgeArray[i][2];
        }

        int [][]result = graph1.KruskalMST();

        for (int i = 0; i < (nodeCnt-1); i++) {
            EdgeCV edgeCV = new EdgeCV(getContext()
                    , nodeDMMap.get(result[i][0]).getX()
                    , nodeDMMap.get(result[i][0]).getY()
                    , nodeDMMap.get(result[i][1]).getX()
                    , nodeDMMap.get(result[i][1]).getY()
            );
            rootViewEdge.addView(edgeCV);
        }

    }

    private void applyAlgorithmMM(AlgorithmDM selectedAlgorithm) {
        BufferedReader bufReader = null;

        int nodeCnt, edgeCnt;
        nodeCnt = nodeCVList.size();
        edgeCnt = edgeCVList.size();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(1);
        stringBuilder.append("\n");
        stringBuilder.append(nodeCnt);
        stringBuilder.append("\n");
        stringBuilder.append(edgeCnt);
        stringBuilder.append("\n");


        Map<Integer, NodeDM> nodeDMMap = new HashMap<>();

        int[][] edgeArray = new int[edgeCnt][2];


        //map each node to a number
        for (int i = 0; i < nodeCnt; i++) {
            NodeDM nodeDM = nodeCVList.get(i).getNodeInfo();
            nodeDMMap.put(i, nodeDM);
        }

        //set array of edge according to map of node
        for (int j = 0; j < edgeCnt; j++) {
            int start = -1;
            int end = -1;
            float[] position = new float[4];

            position = edgeCVList.get(j).getEdgePosition();

            for (int i = 0; i < nodeCnt; i++) {
                if (nodeDMMap.get(i).getX() == position[0] &&
                        nodeDMMap.get(i).getY() == position[1]) {
                    start = i;
                }
                if (nodeDMMap.get(i).getX() == position[2] &&
                        nodeDMMap.get(i).getY() == position[3]) {
                    end = i;
                }
            }

            edgeArray[j][0] = start;
            edgeArray[j][1] = end;
            stringBuilder.append(start + " " + end + "\n");

        }


        bufReader = new BufferedReader(new StringReader(stringBuilder.toString()));

//        bufReader = new BufferedReader(
//                new StringReader(
//                        "1\n10\n10\n0 1\n0 2\n2 3\n3 4\n4 5\n5 0\n6 1\n4 7\n8 1\n7 9\n"));

        EdmondBlossomMaxMatch dsp = new EdmondBlossomMaxMatch();
        try {
            int totalGraphs = dsp.readTotalGraphCount(bufReader);
            dsp.readNextGraph(bufReader);
            dsp.edmondExec();
            String result = dsp.getResult();
            ArrayList<MNode> mNodes = dsp.getResultModel();

            int[][] ee = new int[edgeCnt][2];
            for (int i = 0; i < mNodes.size(); i++) {
                ee[i][0] = mNodes.get(i).label;
                ee[i][1] = mNodes.get(i).getMatchedWith().label;
            }
//            int[][] sortedArray = new int[edgeCnt][2];
//
//            sortedArray = Sort2DMatrix.sortRowWise(ee);

            for (int i = 0; i < mNodes.size(); i++) {
                EdgeCV edgeCV = new EdgeCV(getContext()
                        , nodeDMMap.get(ee[i][0]).getX()
                        , nodeDMMap.get(ee[i][0]).getY()
                        , nodeDMMap.get(ee[i][1]).getX()
                        , nodeDMMap.get(ee[i][1]).getY()
                );
                rootViewEdge.addView(edgeCV);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void showGetEdgeWeightDialog() {
        View view = getLayoutInflater().inflate(R.layout.sheet_edge_weight, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);
        dialog.show();


        final EditText weight = view.findViewById(R.id.sheet_edge_weight_weight);
        TextView ok = view.findViewById(R.id.sheet_edge_weight_ok);
        TextView cancel = view.findViewById(R.id.sheet_edge_weight_cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (weight.getText().toString().length() < 1) {
                    weight.setError("enter edge weight");
                } else {
                    EdgeCV edgeCV = new EdgeCV(getContext(), sx
                            , sy, ex, ey, Double.parseDouble(weight.getText().toString()));
                    rootViewEdge.addView(edgeCV);
                    edgeCVList.add(edgeCV);
                    dialog.dismiss();
                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSelected = false;
                endSelected = false;
                dialog.dismiss();
            }
        });

    }

    private void showHelpDialog() {

        TextView ok;

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_help, null);

        final AlertDialog.Builder alertdialog = new
                AlertDialog.Builder(getActivity());
        alertdialog.setView(view);
        alertdialog.setCancelable(true);

        ok = view.findViewById(R.id.help_dialog_ok);


        final AlertDialog alert = alertdialog.create();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });

        alert.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_white_background));
        alert.show();
    }

}
