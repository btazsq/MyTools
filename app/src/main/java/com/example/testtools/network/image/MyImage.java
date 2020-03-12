package com.example.testtools.network.image;

import android.graphics.Bitmap;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testtools.network.requestion.BTArequest;
import com.example.testtools.network.requestion.RequestionBuilder;

import java.util.ArrayList;
import java.util.List;

public class MyImage {

    private static List<Bitmap> bitmapList = new ArrayList<Bitmap>();

    public static List<Bitmap> getBitmapBuffer(){
        return  bitmapList;
    }

    public static void delBitmapBuffer(){
        bitmapList = null;
    }

    public static void test(){

    }

    public static MyImageTool with(View view){
        if (BTArequest.getOnlyEmbody() == null)
            new RequestionBuilder().build();
        return new MyImageTool(view);
    }

    public static MyImageTool with(AppCompatActivity appCompatActivity){
        if (BTArequest.getOnlyEmbody() == null)
            new RequestionBuilder().build();
        return new MyImageTool(appCompatActivity);
    }

}
