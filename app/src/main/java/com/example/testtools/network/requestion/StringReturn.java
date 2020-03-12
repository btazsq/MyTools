package com.example.testtools.network.requestion;

public class StringReturn{

    protected String object = "";

    protected boolean isEnd = false;

    protected OnRequestion requestion = null;

    public OnRequestion getRequestion() {
        return requestion;
    }

    public StringReturn setRequestion(OnRequestion requestion) {
        this.requestion = requestion;
        return this;
    }

    public StringReturn(){ }

    public StringReturn(OnRequestion onRequestion){
        this.requestion = onRequestion;
    }

    public StringReturn setData(String data){
        object = data;
        return this;
    }

    public String getBack() {
        return object;
    }

    public boolean isEnd() {
        return isEnd;
    }
}
