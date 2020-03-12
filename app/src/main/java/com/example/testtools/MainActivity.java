package com.example.testtools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testtools.network.image.MyImage;
import com.example.testtools.network.requestion.*;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity**********";

    StringReturn stringReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //测试
        MyImage.with(this)
                .setPreView(R.mipmap.ic_launcher)
                .load("https://s2.ax1x.com/2020/02/08/1Wnh36.png")
                .into(findViewById(R.id.image_test))
                .addBuffer();
        //MyImage.getBitmapBuffer().get(0);
        MyImage.delBitmapBuffer();
    }
}
