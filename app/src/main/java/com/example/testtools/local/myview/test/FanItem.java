package com.example.testtools.local.myview.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.testtools.R;

public class FanItem extends View {
    private static final String TAG = "*********FanItem";

    private float oneAn = 0;
    private float twoAn = 0;
    private int color = 0;

    private Paint paint = new Paint();

    public FanItem(Context context) {
        this(context,null);
    }

    public FanItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FanItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FanItem,defStyleAttr,0);
        try {
            int indexCount = typedArray.getIndexCount();
            for (int i=0;i<indexCount;i++){
                int attr = typedArray.getIndex(i);
                switch (attr){
                    case R.styleable.FanItem_oneAn:
                        this.oneAn = typedArray.getFloat(attr,-1);
                        break;
                    case R.styleable.FanItem_twoAn:
                        this.twoAn = typedArray.getFloat(attr,-1);
                        break;
                    case R.styleable.FanItem_itemColor:
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
        float maxAn = Math.max(oneAn,twoAn);
        float minAn = Math.min(oneAn,twoAn);
        float dAn = minAn - maxAn;
        RectF rectF = new RectF();
        rectF.set(0f,0f,getWidth(),getHeight());
        canvas.drawArc(rectF,minAn,dAn,true,paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = Math.min(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(size,size);
    }
}
