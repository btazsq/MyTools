package com.example.testtools.network.requestion;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public final class BTArequest{//BTA是我的网名(
    private static final String TAG = "BTArequest******";
    private static final int SGIN_CALLBACK = 0;//注册回调监听

    protected static BTArequest onlyEmbody = null;

    protected ExecutorService pool = null;

    public static final String TYPE_FORM = "application/x-www-form-urlencoded;charset=utf-8";
    public static final String TYPE_JSON = "application/json;charset=utf-8";

    protected String cType = TYPE_JSON;
    protected int outTime = 3000;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == SGIN_CALLBACK && (OnRequestion)msg.obj!=null)
            ((OnRequestion)msg.obj).onReCallBack();
        }
    };

    public HttpURLConnection openConnection(String web) throws Exception {
        URL url = new URL(web);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(outTime);
        connection.setConnectTimeout(outTime);
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type",cType);
        return connection;
    }

    //自由提交任务
    public Future submitTask(final OnRequestion.DefineAction action){
        Future future = submitTask(action,null);
        return future;
    }

    //自由提交任务
    public Future submitTask(final OnRequestion.DefineAction action, final OnRequestion requestion){
        Future future = pool.submit(new Runnable() {
            @Override
            public void run(){
                action.howDo();
                if (requestion != null){
                    callBackByHandler(requestion);
                }
            }
        });
        return future;
    }

    public BTAConnection fromWeb(String web){
        return new BTAConnection(web);
    }

    public BTArequest setcType(String cType) {
        this.cType = cType;
        return this;
    }

    protected BTArequest(){ }

    public static BTArequest getOnlyEmbody(){return onlyEmbody;}

    public void callBackByHandler(OnRequestion onRequestion){
        Message meg = new Message();
        meg.what = SGIN_CALLBACK;
        meg.obj = onRequestion;
        handler.sendMessage(meg);
    }

    public class BTAConnection{
        private boolean isPost = false;

        private String web;

        private HttpURLConnection thisConnection = null;

        private long whileTime = 50L;//POST时，get数据的循环等待时间

        private String requestMethod = "GET";

        public BTAConnection setWhileTime(long time){
            whileTime = time;
            return this;
        }

        public BTAConnection setRequestMethod(String method){
            try {
                requestMethod = method;
                thisConnection.setRequestMethod(method);
            } catch (ProtocolException e) {
                Log.d(TAG, "setRequestMethod: " + e.toString());
            }
            return this;
        }

        public BTAConnection setRequestProperty(String key,String value){
            thisConnection.setRequestProperty(key,value);
            return this;
        }

        protected BTAConnection(String web){
            this.web = web;
            try {
                thisConnection = openConnection(web);
            } catch (Exception e) {
                Log.d(TAG, "BTAConnection: " + e.toString());
            }
        }

        public String getWeb(){ return web; }

        public BTAConnection postData(StringReturn stringReturn){
            isPost = true;
            getOnlyEmbody().submitTask(()->{
                try{
                    thisConnection.setRequestMethod("POST");
                    thisConnection.connect();
                    DataOutputStream dataOutputStream = new DataOutputStream(thisConnection.getOutputStream());
                    dataOutputStream.writeBytes(stringReturn.object);
                    dataOutputStream.flush();
                    dataOutputStream.close();
                }catch (Exception e){
                    Log.d(TAG, "postData: " + e.toString());
                }finally {
                    try {
                        thisConnection.setRequestMethod(requestMethod);
                    } catch (ProtocolException e) {
                        Log.d(TAG, "postData: " + e.toString());
                    }
                }
            },()->{
                isPost = false;
                if (stringReturn.requestion != null)
                stringReturn.requestion.onReCallBack();
            });
            return this;
        }

        public BTAConnection getStringReturn(StringReturn stringReturn){
            getOnlyEmbody().submitTask(()->{
                if (this.thisConnection != null){
                    try {
                        while (isPost){
                            Thread.sleep(whileTime);
                        }
                        thisConnection.connect();
                        int k = thisConnection.getResponseCode();
                        if (k==200){
                            InputStream in = thisConnection.getInputStream();
                            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                            StringBuilder response=new StringBuilder();
                            String line;
                            while ((line=reader.readLine())!=null){
                                response.append(line);
                            }
                            in.close();
                            reader.close();
                            stringReturn.object = response.toString();
                            stringReturn.isEnd = true;
                        }
                    }catch (Exception e){
                        Log.d(TAG, "getStringReturn: *********" + e.toString());
                    }
                }
            },stringReturn.requestion);
            return this;
        }

        public BTArequest disconnect(){
            submitTask(()->{
                thisConnection.disconnect();
            },()->{ });
            return getOnlyEmbody();
        }

    }

}
