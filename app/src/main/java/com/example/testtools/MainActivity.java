package com.example.testtools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.testtools.network.requestion.*;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity**********";

    StringReturn stringReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //测试
        stringReturn = new StringReturn(()->{
            Toast.makeText(MainActivity.this,stringReturn.getBack(),Toast.LENGTH_LONG).show();
            Log.d("******", "onCreate: " + stringReturn.getBack());
        });
        new RequestionBuilder()
                .build()
                .setcType(BTArequest.TYPE_FORM)
                .fromWeb("http://bihu.jay86.com/login.php")
                .setWhileTime(50L)
                .setRequestMethod("POST")
                .postData(new StringReturn()
                        .setData("username=zsq5&password=zsqzsq"))
                .getStringReturn(stringReturn)
                .disconnect();
    }
}
