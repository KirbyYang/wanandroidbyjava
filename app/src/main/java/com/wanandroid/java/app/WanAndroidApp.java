package com.wanandroid.java.app;

import android.app.Application;

import com.wanandroid.java.base.init.MvpManager;


/**
 * Created by YZH on 2019/4/10.
 * From the BaoBao project
 *
 * @author YZH
 */
public class WanAndroidApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        MvpManager.init(this);
    }
}
