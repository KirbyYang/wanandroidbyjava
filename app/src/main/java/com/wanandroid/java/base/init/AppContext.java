package com.wanandroid.java.base.init;

import android.content.Context;

/**
 * Created by godliness on 2019/1/12.
 * From the BaoBao project
 *
 * @author godliness
 *         应用程序级上下文
 */

public final class AppContext {

    private static Context sContext;

    public static void register(Context cxt) {
        sContext = cxt;
    }

    public static Context get() {
        return sContext;
    }
}
