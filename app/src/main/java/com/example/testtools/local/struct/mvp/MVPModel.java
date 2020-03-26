package com.example.testtools.local.struct.mvp;

import android.util.Log;
import android.widget.Toast;

public class MVPModel implements MVPContact.Model{

    MVPContact.Presenter presenter;

    @Override
    public void saveSomething() {
        Log.d("Model*****", "saveSomething: ");
    }

    @Override
    public void setPresenter(MVPContact.Presenter presenter) {
        this.presenter = presenter;
    }
}
