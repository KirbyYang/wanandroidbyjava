package com.longrise.android.bbt.lib_mvp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by godliness on 2019/1/10.
 * From the BaoBao project
 *
 * @author godliness
 *         网络状态
 */

public class NetUtil {

    /**
     * 网络是否可用
     *
     * @return true 可用
     */
    public static boolean isNetWorkEnable(Context cxt) {
        if (cxt != null) {
            ConnectivityManager manager = (ConnectivityManager) cxt.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager == null) {
                return false;
            }
            NetworkInfo networkinfo = manager.getActiveNetworkInfo();
            if (networkinfo != null && networkinfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}
