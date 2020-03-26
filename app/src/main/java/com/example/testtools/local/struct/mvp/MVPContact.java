package com.example.testtools.local.struct.mvp;

public interface MVPContact {

    interface Presenter{

        void changeSomething();

        void saveSomething();

        void setView(MVPContact.View view);

        void setModel(MVPContact.Model model);

    }

    interface View{

        void changeSomething();

        void setPresenter(MVPContact.Presenter presenter);

    }

    interface Model{

        void saveSomething();

        void setPresenter(MVPContact.Presenter presenter);

    }

}
