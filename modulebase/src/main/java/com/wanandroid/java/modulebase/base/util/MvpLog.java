package com.wanandroid.java.modulebase.base.util;

import android.util.Log;

import com.wanandroid.java.modulebase.BuildConfig;


/**
 * Created by godliness on 2018/12/31.
 * From the BaoBao project
 *
 * @author godliness
 */

public final class MvpLog {


    private static final boolean OPEN_ABLE = BuildConfig.DEBUG;

    /**
     * 以级别为 v 的形式输出LOG
     */
    public static void v(String tag, String msg) {
        if (OPEN_ABLE) {
            Log.v(tag, msg);
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d(String tag, String msg) {
        if (OPEN_ABLE) {
            Log.d(tag, msg);
        }
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    public static void i(String tag, String msg) {
        if (OPEN_ABLE) {
            Log.i(tag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public static void w(String tag, String msg) {
        if (OPEN_ABLE) {
            Log.w(tag, msg);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String tag, String msg) {
        if (OPEN_ABLE) {
            Log.e(tag, msg);
        }
    }

    /**
     * 打印异常日志
     */
    public static void print(Throwable throwable) {
        if (OPEN_ABLE) {
            throwable.printStackTrace();
        }
    }
}
