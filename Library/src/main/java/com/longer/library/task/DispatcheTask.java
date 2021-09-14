package com.longer.library.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import com.longer.library.build.RequestBuild;
import com.longer.library.error.GlideException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

public class DispatcheTask extends Thread{


    private LinkedBlockingQueue<RequestBuild> requestQueue;
    private Handler handler;

    public DispatcheTask(LinkedBlockingQueue<RequestBuild> requestQueue){
        this.requestQueue = requestQueue;
        // 获取主线程Looper  保证UI刷新
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {
        super.run();


        // 线程没有中断，一直轮询
        if(!isInterrupted()){
            try {
                RequestBuild take = requestQueue.take();
                // 设置展位图
                placeHold(take);

                // 获取网络图片
                Bitmap bitmap = loadFromNet(take);
                // 加载网络图片
                setBitmap(take, bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 加载网络图片
    private void setBitmap(RequestBuild take, Bitmap bitmap) {
        if(take.getImageView() != null
                && bitmap != null
                && take.getImageView().getTag().equals(take.getMd5fileName())){
                handler.post(() -> {
                    take.getImageView().setImageBitmap(bitmap);
                    take.getListener().OnResourceRead();
                });
        }

    }

    private Bitmap loadFromNet(RequestBuild take) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(take.getUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);

        } catch (Exception e) {
            e.printStackTrace();
            take.getListener().OnLoadFailed(new GlideException("获取网络图片失败！"));
        } finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }

    private void placeHold(RequestBuild take) {
        if(take.getPleaceResourceId()>=0 && take.getImageView() != null){
            handler.post(() -> take.getImageView().setImageResource(take.getPleaceResourceId()));
        }

    }
}
