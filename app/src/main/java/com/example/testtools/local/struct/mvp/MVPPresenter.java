package com.example.testtools.local.struct.mvp;

public class MVPPresenter implements MVPContact.Presenter{

    MVPContact.View view;
    MVPContact.Model model;

    public MVPPresenter(MVPContact.View view){
        setView(view);
        setModel(new MVPModel());
        model.setPresenter(this);
    }

    @Override
    public void changeSomething() {
        //do something
        if (view != null)
        view.changeSomething();
    }

    @Override
    public void saveSomething() {
        //do something
        if (model != null)
        model.saveSomething();
    }

    @Override
    public void setView(MVPContact.View view) {
        this.view = view;
    }

    @Override
    public void setModel(MVPContact.Model model) {
        this.model = model;
    }
}
