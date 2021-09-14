package com.longer.library.build;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.longer.library.listener.RequestListener;
import com.longer.library.manager.RequestManage;
import com.longer.library.utils.MD5Utils;

import java.lang.ref.SoftReference;

public class RequestBuild {
    private final Context context;
    private String url;
    private String md5fileName;
    private int pleaceResourceId;
    private SoftReference<ImageView> imageView;
    private RequestListener listener;



    public RequestBuild(Context context) {
        this.context = context;
    }

    public RequestBuild load(String url) {
        this.url = url;
        this.md5fileName = MD5Utils.toMD5(url);
        return this;
    }

    public RequestBuild placehold(int pleaceResourceId) {
        this.pleaceResourceId = pleaceResourceId;
        return this;
    }

    public RequestBuild listener(RequestListener listener) {
        this.listener = listener;
        return this;
    }

    public void into(ImageView imageView){
        imageView.setTag(md5fileName);
        this.imageView = new SoftReference<>(imageView);

        if(TextUtils.isEmpty(url)){
            //todo 抛异常
        }
        if(pleaceResourceId <= 0){

        }

        RequestManage.getInstance().addRequestQueen(this).dispatcher();

    }


    public Context getContext() {
        return context;
    }

    public String getUrl() {
        return url;
    }

    public String getMd5fileName() {
        return md5fileName;
    }

    public int getPleaceResourceId() {
        return pleaceResourceId;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public RequestListener getListener() {
        return listener;
    }
}
