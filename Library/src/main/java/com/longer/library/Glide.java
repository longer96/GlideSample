package com.longer.library;

import android.content.Context;

import com.longer.library.manager.RequestManage;

public class Glide {

    public static RequestManage with(Context context){
        return RequestManage.getInstance().get(context);
    }

}
