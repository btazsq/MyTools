package com.example.testtools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.example.testtools.local.media.MediaService;
import com.example.testtools.local.struct.mvp.MVPActivity;
import com.example.testtools.local.struct.mvp.MVPContact;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity**********";

    MediaService mediaService = null;

    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            binder.changeCD(binder.getCD()>0x1000?0:R.raw.kksk);
        }
    };

    MediaService.MyBinder binder = null;

    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MediaService.MyBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void MediaInit(){
        Button button = findViewById(R.id.change);
        button.setOnClickListener(clickListener);
        bindService(new Intent(this,MediaService.class),connection,BIND_AUTO_CREATE);
        mediaService = new MediaService(R.raw.kksk);
        startService(new Intent(MainActivity.this,MediaService.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //测试
        startActivity(new Intent(MainActivity.this, MVPActivity.class));
    }
}