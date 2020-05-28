package com.dev.mostafa.maximummatching.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.dev.mostafa.maximummatching.R;

public class EdgeCV extends View {
    Paint paintLine = new Paint();
    Paint paintText = new Paint();
    float startX , startY, endX , endY = 0;


    public EdgeCV(Context context , float sx , float sy , float ex , float ey) {
        super(context);
        layoutInhalator();
        startX = sx;
        startY = sy;
        endX = ex;
        endY = ey;
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
        canvas.drawText("line 1" , (startX + endX)/2 ,
                (startY + endY)/2  , paintText);
    }

}
