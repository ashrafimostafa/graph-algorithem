package com.dev.mostafa.maximummatching.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.dev.mostafa.maximummatching.R;
import com.dev.mostafa.maximummatching.model.EdgeDM;

public class EdgeCV extends View {
    Paint paintLine = new Paint();
    Paint paintText = new Paint();
    float startX , startY, endX , endY = 0;
    double weight;


    public EdgeCV(Context context , float sx , float sy
            , float ex , float ey , double weight ) {
        super(context);
        layoutInhalator();
        startX = sx;
        startY = sy;
        endX = ex;
        endY = ey;
        this.weight = weight;
    }




    public void layoutInhalator(){
        paintLine.setColor(getResources().getColor(R.color.colorPrimary));
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(10);
        paintLine.setAntiAlias(true);

        paintText.setColor(Color.BLACK);
        paintText.setTextSize(25);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(startX , startY , endX , endY , paintLine);
        canvas.drawText(weight + "" , (startX + endX)/2 ,
                (startY + endY)/2  , paintText);
    }

    public EdgeDM getEdgeInfo(){
        return new EdgeDM(
                1
                , 1
                ,1
                ,"a"
                ,weight
        );
    }

    public float[] getEdgePosition(){
        float[]position = new float[4];
        position[0] = startX;
        position[1] = startY;
        position[2] = endX;
        position[3] = endY;
        return position;
    }

}
