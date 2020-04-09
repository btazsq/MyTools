package com.example.testtools.local.myview.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

public class FanMap extends View {

    private List<RectElement> rectElementList = new LinkedList<RectElement>();

    private Paint paint = new Paint();

    public FanMap(Context context) {
        super(context);
    }

    public FanMap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FanMap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FanMap addElement(RectElement rectElement){
        rectElementList.add(rectElement);
        postInvalidate();
        return this;
    }

    public FanMap delAll(){
        rectElementList.clear();
        postInvalidate();
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        int l = Math.min(getHeight(),getWidth());
        int n = rectElementList.size();
        float total = 0;
        float a = 0;
        for(int i=0;i<n;i++){
            RectElement e = rectElementList.get(i);
            total += e.getPercent();
        }
        for(int i=0;i<n;i++){
            RectElement e = rectElementList.get(i);
            paint.setColor(e.getColor());
            canvas.drawArc(0,0,l,l,a,360*e.getPercent()/total,true,paint);
            a += 360*e.getPercent()/total;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = Math.min(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
        setMeasuredDimension(size,size);
    }
}
