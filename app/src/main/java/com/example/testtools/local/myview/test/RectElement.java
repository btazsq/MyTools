package com.example.testtools.local.myview.test;

import java.util.Random;

public class RectElement {

    public RectElement(float percent){
        this(percent, new Random().nextInt()%(0xffffff)+0xff000000);
    }

    public RectElement(float percent, int color) {
        this.percent = percent;
        this.color = color;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private float percent;

    private int color;
}