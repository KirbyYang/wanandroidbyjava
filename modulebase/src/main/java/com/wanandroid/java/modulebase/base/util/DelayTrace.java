package com.wanandroid.java.modulebase.base.util;


import com.wanandroid.java.modulebase.BuildConfig;

/**
 * Created by godliness on 2019/1/17.
 * From the BaoBao project
 *
 * @author godliness
 *         用于辅助打印方法超时
 */

public class DelayTrace {

    private static final String TAG = "DelayTrace";

    private static long sBeginTime = 0;

    /**
     * 开始计时，该方法要成对出现记为一次超时时间
     */
    public static void begin() {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (sBeginTime == 0) {
            sBeginTime = System.currentTimeMillis();
        } else {
            Long deley = System.currentTimeMillis() - sBeginTime;
            sBeginTime = 0;
            MvpLog.e(TAG, "delay: " + deley);
        }
    }
}
