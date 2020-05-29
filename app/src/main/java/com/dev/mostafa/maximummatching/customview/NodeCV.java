package com.dev.mostafa.maximummatching.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dev.mostafa.maximummatching.R;
import com.dev.mostafa.maximummatching.model.NodeDM;

public class NodeCV extends RelativeLayout {

    View view;
    TextView name;
    int id;


    public NodeCV(Context context) {
        super(context);
        layoutInhalator();

    }

    public NodeCV(Context context, AttributeSet attrs ) {
        super(context, attrs);
        layoutInhalator();
    }

    public NodeCV(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        layoutInhalator();
    }

    public NodeCV(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        layoutInhalator();
    }

    public void layoutInhalator(){
        view = LayoutInflater.from(getContext()).inflate(R.layout.customview_node ,this,true);
        name = view.findViewById(R.id.customview_node_name);
    }

    public void setNode(String nodeName , float x , float y){
        view.setX(x);
        view.setY(y);
        name.setText(nodeName);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public NodeDM getNodeInfo(){
        //TODO exchange 10 with half of node size
        return new NodeDM(name.getText().toString() , view.getX() , view.getY() , 1 );
    }
}
