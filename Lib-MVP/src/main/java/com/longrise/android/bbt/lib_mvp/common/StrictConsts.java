package com.longrise.android.bbt.lib_mvp.common;

import android.content.Intent;
import android.os.Bundle;

import com.longrise.android.bbt.lib_mvp.BuildConfig;
import com.longrise.android.bbt.lib_mvp.cache.BundlePool;
import com.longrise.android.bbt.lib_mvp.util.MvpLog;

/**
 * Created by godliness on 2018/12/31.
 * From the BaoBao project
 *
 * @author godliness
 *         辅助debug模式下相关检查
 */

public class StrictConsts {

    /**
     * 是否开启检查模式
     */
    private static final boolean OPEN_STRICT = false;
    public static final boolean STRICT = BuildConfig.DEBUG && OPEN_STRICT;

    /**
     * 捕获相关异常
     */
    public static void releaseCatchException(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            MvpLog.print(throwable);
        }
    }

    /**
     * 检查Bundle来源
     */
    public static void checkStartActivityForResult(Intent intent) {
        if (STRICT) {
            Bundle extra = intent.getExtras();
            boolean bundleFrom = extra.getBoolean(BundlePool.EXTRA_FROM);
            if (!bundleFrom) {
                throw new IllegalArgumentException("please use BundlePool().obtion(), get an instance");
            }
        }
    }

    /**
     * 检查广播注册
     *
     * @param register 是否注册广播
     */
    public static void checkRegisterReceiver(boolean register) {
        if (STRICT) {
            if (register) {
                throw new RuntimeException("please registerReceiver() use ReceiverUitl");
            }
            throw new IllegalArgumentException("please unregisterReceiver() use ReceiverUitl");
        }
    }
}
