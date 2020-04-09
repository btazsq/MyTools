package com.example.testtools.local.myview.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.testtools.R;

public class RectItem extends View {
    private static final String TAG = "*********RectItem";

    private Paint paint = new Paint();

    private float board = 0;
    private float high = 0;
    private int color = 0;
    
    public RectItem(Context context) {
        this(context,null);
    }

    public RectItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RectItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RectItem,defStyleAttr,0);
        try {
            int indexCount = typedArray.getIndexCount();
            for (int i=0;i<indexCount;i++){
                int attr = typedArray.getIndex(i);
                Log.d(TAG, "RectItem: "+typedArray.getFloat(attr,-1));
                switch (attr){
                    case R.styleable.RectItem_board:
                        this.board = typedArray.getFloat(attr,-1);
                        break;
                    case R.styleable.RectItem_high:
                        this.high = typedArray.getFloat(attr,-1);
                        break;
                    case R.styleable.RectItem_itemColor:
                        this.color = typedArray.getColor(attr,0);
                        break;
                }
            }
        }catch (Exception e){
            Log.d(TAG, "RectItem: "+e.toString());
        }finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        int h = getHeight();
        int w = getWidth();
        canvas.drawRect((float) (0.5-board/2)*w,(1-high)*h,(float) (0.5+board/2)*w,h,paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (MeasureSpec.getMode(widthMeasureSpec)==MeasureSpec.AT_MOST){
            widthMeasureSpec = Math.max(widthMeasureSpec, 20);
        }
        if (MeasureSpec.getMode(heightMeasureSpec)==MeasureSpec.AT_MOST){
            heightMeasureSpec = Math.max(heightMeasureSpec, 100);
        }
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }
}
