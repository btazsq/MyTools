package com.example.testtools.local.struct.mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testtools.R;

public class MVPActivity extends AppCompatActivity implements MVPContact.View{

    MVPContact.Presenter presenter = new MVPPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_v_p);
        this.setPresenter(presenter);
        Button change = findViewById(R.id.mvp_button1);
        Button save = findViewById(R.id.mvp_button2);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.changeSomething();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveSomething();
            }
        });
    }

    @Override
    public void changeSomething() {
        Toast.makeText(MVPActivity.this,"View更新视图",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(MVPContact.Presenter presenter) {
        this.presenter = presenter;
    }
}
