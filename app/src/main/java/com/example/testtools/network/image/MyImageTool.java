package com.example.testtools.network.image;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyImageTool {
    private static final int NULL = 0;

    private static final int CHANGE_UI = 0xF1;

    private static final int SET_PREVIEW = 0xF2;

    private static final int MODE_VIEW = 1;

    private static final int MODE_ACTIVITY = 2;

    private int mode;

    private View view;

    private AppCompatActivity appCompatActivity;

    private int preview = 0;

    private Bitmap bitmap = null;

    private boolean isDoingTask = false;

    private MyThreadTask threadTask = new MyThreadTask(null);

    protected MyImageTool(){
        this.mode = NULL;
    }

    protected MyImageTool(View view){
        this.view = view;
        this.mode = MODE_VIEW;
    }

    protected MyImageTool(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
        this.mode = MODE_ACTIVITY;
    }

    public MyImageTool setPreView(int img){
        preview = img;
        return this;
    }

    public MyImageTool setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        return this;
    }

    public MyImageTool load(String url){
        isDoingTask = true;
        threadTask.submitTask(new MyThreadTask(()->{
            //
            Bitmap bit = null;
            bit = getBitmap(url);
            if (bit != null){
                bitmap = bit;
            }
        }));
        return this;
    }

    public MyImageTool load(Bitmap bitmap){
        isDoingTask = true;
        threadTask.submitTask(new MyThreadTask(()->{
            this.bitmap = bitmap;
        }));
        return this;
    }

    public MyImageTool into(ImageView imageView){
        if (imageView == null)return this;
        if (preview != NULL) {
            Message mes = new Message();
            mes.what = SET_PREVIEW;
            mes.obj = imageView;
            mes.arg1 = preview;
            handler.sendMessage(mes);
        }
        threadTask.submitTask(new MyThreadTask(()->{
            Message message = new Message();
            message.what = CHANGE_UI;
            message.obj = imageView;
            handler.sendMessage(message);
            isDoingTask = false;
        }));
        return this;
    }

    public MyImageTool into(int id){
        ImageView imageView = null;
        switch (mode){
            case NULL:return this;
            case MODE_VIEW:{
                imageView = (ImageView) this.view.findViewById(id);
            }break;
            case MODE_ACTIVITY:{
                imageView = (ImageView) this.appCompatActivity.findViewById(id);
            }break;
            default:break;
        }
        if (imageView == null)return this;
        if (preview != NULL) {
            Message mes = new Message();
            mes.what = SET_PREVIEW;
            mes.obj = imageView;
            mes.arg1 = preview;
            handler.sendMessage(mes);
        }
        ImageView finalImageView = imageView;
        threadTask.submitTask(new MyThreadTask(()->{
            Message message = new Message();
            message.what = CHANGE_UI;
            message.obj = finalImageView;
            handler.sendMessage(message);
            isDoingTask = false;
        }));
        return this;
    }

    public MyImageTool addBuffer(){
        threadTask.submitTask(new MyThreadTask(()->{
            if (bitmap != null)
                MyImage.bitmapList.add(bitmap);
        }));
        return this;
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CHANGE_UI:{
                    ((ImageView)msg.obj).setImageBitmap(bitmap);
                }break;
                case SET_PREVIEW:{
                    ((ImageView)msg.obj).setImageResource(msg.arg1);
                }break;
                default:break;
            }
        }
    };

    public static Bitmap getBitmap(String url){
        Bitmap bitmap = null;
        URL imgUrl = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
