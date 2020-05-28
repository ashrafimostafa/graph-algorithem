package com.dev.mostafa.maximummatching.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class EdgeCV extends View {
    Paint paint = new Paint();
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
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(startX , startY , endX , endY , paint);
    }

}
