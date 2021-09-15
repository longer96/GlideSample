package com.longer.library.manager;

import android.content.Context;
import android.util.Log;

import com.longer.library.build.RequestBuild;
import com.longer.library.task.DispatcheTask;

import java.util.concurrent.LinkedBlockingQueue;

public class RequestManage {
    private static RequestManage instance;

    // 暂时没有用上，Glide有内存回收机制，会通过context绑定注册Fragment，自动管理生命周期
    private Context context;
    private LinkedBlockingQueue<RequestBuild> requestQueue = new LinkedBlockingQueue<RequestBuild>();
    private DispatcheTask[] tasks;


    private RequestManage() {
    }

    public static RequestManage getInstance(){
        if(instance == null) {
            synchronized (RequestManage.class) {
                instance = new RequestManage();
            }
        }
        return instance;
    }


    public RequestManage get(Context context) {
        this.context = context;
        return this;
    }

    public RequestBuild load(String url){
        return new RequestBuild(context).load(url);
    }



    public RequestManage addRequestQueen(RequestBuild requestBuild) {
        if(!requestQueue.contains(requestBuild)){
            requestQueue.add(requestBuild);
        }
        return this;
    }

    // 触发任务
    public void dispatcher(){
        // 获取最大可用线程数量
        int threadCount = Runtime.getRuntime().availableProcessors();
        tasks = new DispatcheTask[threadCount];

        Log.d("TAG", "ThreadCount: " + threadCount);

        // todo 为什么要开这么多线程
        // 有点类似于银行柜台业务 threadCount：模拟器跑出来等于2
        for (int i = 0; i < threadCount; i++) {
            Log.d("TAG", "i: " + i);
            DispatcheTask task = new DispatcheTask(requestQueue);
            task.start();

            tasks[i] = task;
        }

    }

    public void stop(){
        for (DispatcheTask task : tasks) {
            if(!task.isInterrupted()){
                task.interrupt();
            }
        }
    }

}
