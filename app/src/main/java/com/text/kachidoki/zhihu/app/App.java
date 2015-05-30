package com.text.kachidoki.zhihu.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.android.http.RequestManager;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Frank on 15/4/27.
 */
public class App extends Application {
    private static App instance;

    public static App getInstance(){

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //一些包的初始化
        RequestManager.getInstance().init(this);
        RequestManager.getInstance().setDebugMode(true,"ZhiHuNet");
        Fresco.initialize(this);
    }

    public void save(String token,String name){
        SharedPreferences sp = getSharedPreferences("app",MODE_PRIVATE);
        sp.edit().putString("token", token).commit();
        sp.edit().putString("name",name).commit();
    }
    public String getToken(){
        SharedPreferences sp = getSharedPreferences("app",MODE_PRIVATE);
        return sp.getString("token","");
    }
    public String getName(){
        SharedPreferences sp = getSharedPreferences("app",MODE_PRIVATE);
        return sp.getString("name","账号");
    }
}
