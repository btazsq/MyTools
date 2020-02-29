package com.example.testtools.network.requestion;

import java.util.concurrent.Executors;

public final class RequestionBuilder {
    private static final int THREAD_NUM = 16;//线程池线程数量

    private static final int LEAST_BUILD = 0;//至少需要多少步骤

    private int step;//剩余需要执行步骤

    private boolean simple = false;//允不允许返回单例

    private BTArequest request = new BTArequest();//对构造的BTArequest对象做缓存

    public RequestionBuilder(){
        this(LEAST_BUILD);
    }

    public RequestionBuilder(int step){
        if (BTArequest.onlyEmbody == null) simple = true;
        this.step = Math.max(step, LEAST_BUILD);
    }

    /**
     * set方法
     */
    public RequestionBuilder setContentType(String type){
        request.cType = type;
        return this;
    }

    public RequestionBuilder setOutTime(int time){
        request.outTime = time;
        return this;
    }

    public BTArequest build(){
        if (step > 0 || !simple)return BTArequest.onlyEmbody;
        request.pool = Executors.newFixedThreadPool(THREAD_NUM);
        BTArequest.onlyEmbody = request;
        return BTArequest.onlyEmbody;
    }

}
