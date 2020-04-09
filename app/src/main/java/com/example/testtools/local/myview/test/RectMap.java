package com.example.testtools.local.myview.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

public class RectMap extends View {

    private static final String TAG = "*********RectMap";

    private List<RectElement> rectElementList = new LinkedList<RectElement>();

    private Paint paint = new Paint();

    public RectMap(Context context) {
        super(context);
    }

    public RectMap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RectMap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RectMap addElement(RectElement rectElement){
        rectElementList.add(rectElement);
        postInvalidate();
        return this;
    }

    public RectMap delAll(){
        rectElementList.clear();
        postInvalidate();
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        int h = getHeight();
        int w = getWidth();
        int n = rectElementList.size()+1;
        paint.setColor(0xffaaaaaa);
        canvas.drawLine(w/n/2f,h,w/n/2f,0,paint);
        canvas.drawLine(0,h*9/10f,w,h*9/10f,paint);
        for (int i=1;i<n;i++) {
            RectElement e = rectElementList.get(i-1);
            paint.setColor(e.getColor());
            canvas.drawRect(i*w/n,h*9f/10f*(1-e.getPercent())+h/10f,(i+1)*w/n,h*9f/10f,paint);
            //Log.d(TAG, h*9f/10f*(1-e.getPercent())+h/10f+" : "+h*9f/10f);
        }
    }
}
