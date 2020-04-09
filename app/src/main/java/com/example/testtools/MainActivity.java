package com.example.testtools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.testtools.local.myview.test.RectElement;
import com.example.testtools.local.myview.test.RectMap;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "**********"+getClass().getSimpleName();

    private RectMap rectMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //测试
        Random random = new Random();
        rectMap = (RectMap)findViewById(R.id.rect_map);
        for (int i=0;i<5;i++){
            rectMap.addElement(new RectElement(random.nextFloat()%1));
        }
    }
}