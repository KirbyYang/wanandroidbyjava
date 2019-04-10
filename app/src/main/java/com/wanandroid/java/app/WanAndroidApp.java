package com.wanandroid.java.app;

import android.app.Application;

import com.longrise.android.bbt.lib_mvp.init.MvpManager;

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
