package com.example.testtools.network.image;

import com.example.testtools.network.requestion.BTArequest;
import com.example.testtools.network.requestion.OnRequestion;
import com.example.testtools.network.requestion.RequestionBuilder;

import java.util.LinkedList;
import java.util.Queue;

public class MyThreadTask {

    private boolean isDoingTask = false;

    public Queue<MyThreadTask> taskQueue = new LinkedList<MyThreadTask>();

    private OnRequestion.DefineAction defineAction;

    private OnRequestion onRequestion;

    public OnRequestion.DefineAction getDefineAction() {
        return defineAction;
    }

    public void setDefineAction(OnRequestion.DefineAction defineAction) {
        this.defineAction = defineAction;
    }

    public OnRequestion getOnRequestion() {
        return onRequestion;
    }

    public void setOnRequestion(OnRequestion onRequestion) {
        this.onRequestion = onRequestion;
    }

    public MyThreadTask(){
        this(()->{},null);
    }

    public MyThreadTask(OnRequestion.DefineAction defineAction){
        this(defineAction,null);
    }

    public MyThreadTask(OnRequestion.DefineAction defineAction,OnRequestion onRequestion){
        this.defineAction = defineAction;
        this.onRequestion = onRequestion;
        if (BTArequest.getOnlyEmbody() == null)
            new RequestionBuilder().build();
    }

    public void submitTask(MyThreadTask task){
        taskQueue.offer(task);
        doTask();
    }

    private void doTask(){
        if (isDoingTask == true)return;
        isDoingTask = true;
        if (BTArequest.getOnlyEmbody() == null)
            new RequestionBuilder().build();
        BTArequest.getOnlyEmbody().submitTask(()->{
            while (taskQueue.peek() != null){
                MyThreadTask threadTask = taskQueue.poll();
                threadTask.getDefineAction().howDo();
                BTArequest.getOnlyEmbody().callBackByHandler(threadTask.getOnRequestion());
            }
        },()->{
            isDoingTask = false;
        });
    }

    public static void postTask(MyThreadTask task){
        if (BTArequest.getOnlyEmbody() == null)
            new RequestionBuilder().build();
        BTArequest.getOnlyEmbody().submitTask(task.getDefineAction(),task.getOnRequestion());
    }

}
