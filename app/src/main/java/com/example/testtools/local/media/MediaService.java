package com.example.testtools.local.media;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testtools.MainActivity;
import com.example.testtools.R;
import com.example.testtools.network.image.MyThreadTask;

import java.io.FileDescriptor;

public class MediaService extends Service {

    private MediaPlayer mediaPlayer = null;

    private MyThreadTask threadTask = new MyThreadTask();

    private int raw = 0;

    AudioManager manager = null;

    public MediaService(){
        this.raw = R.raw.kksk;
    }

    public MediaService(int raw){
        this.raw = raw;
    }

    public MediaService changeCD(int R){
        this.raw = R;
        mediaPlayer = MediaPlayer.create(MediaService.this,R);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
        return this;
    }

    public MediaService start(){
        threadTask.submitTask(new MyThreadTask(()->{
            mediaPlayer.stop();
            mediaPlayer.prepareAsync();
        }));
        return this;
    }

    public MediaService start(int R){
        changeCD(R).start();
        return this;
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            manager.setStreamVolume(AudioManager.STREAM_MUSIC, //音量类型
                    msg.arg1,
                    AudioManager.FLAG_PLAY_SOUND
                            | AudioManager.FLAG_SHOW_UI);
        }
    };

    @Override
    public void onCreate() {
        changeCD(MediaService.this.raw);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public MyBinder binder = new MyBinder(this);

    public class MyBinder extends Binder {

        private MediaService service = null;

        MyBinder(MediaService service){
            this.service = service;
        }

        public void changeCD(int R){
            service.mediaPlayer.stop();
            if (R == 0)service.raw = 0;
            if (R>0x1000)
            service.start(R);
        }

        public int getCD(){
            return service.raw;
        }

    }

    private void test(){
        threadTask.submitTask(new MyThreadTask(()->{
            this.raw = R.raw.kksk;
            manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            int maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            Log.d("******", "e.toString()");
            start();
            for(int i=0;i<700;i++){
                try {
                    Message message = new Message();
                    message.arg1 = maxVolume;
                    Thread.sleep(100L);
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Log.d("******", e.toString());
                }
            }
            mediaPlayer.stop();
            stopSelf();
        }));
    }//送给最好的ta

}
