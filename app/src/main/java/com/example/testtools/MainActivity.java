package com.example.testtools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
        RecyclerView recyclerView = findViewById(R.id.recycle_test);
        TestRecyclerAdpater adpater = new TestRecyclerAdpater();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adpater);
        recyclerView.setLayoutManager(manager);
    }
}
