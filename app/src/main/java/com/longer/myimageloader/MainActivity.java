package com.longer.myimageloader;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.longer.library.Glide;
import com.longer.library.error.GlideException;
import com.longer.library.listener.RequestListener;


/**
 * 配套视频教程
 * P1
 * 90分钟了解glide框架核心原理并手写实现
 * https://www.bilibili.com/video/BV1yE411j7UQ
 */
public class MainActivity extends AppCompatActivity {

    String url = "http://g.hiphotos.baidu.com/image/pic/item/10dfa9ec8a1363270c254f53948fa0ec09fac782.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.img);
        Glide
                .with(this)
                .load(url)
                .placehold(R.mipmap.ic_launcher)
                .listener(new RequestListener() {
                    @Override
                    public void OnResourceRead() {
                        Log.d("TAG", "OnResourceRead: ");
                    }

                    @Override
                    public void OnLoadFailed(GlideException e) {
                        Log.d("TAG", "OnLoadFailed: ");
                    }
                })
                .into(imageView);
    }
}