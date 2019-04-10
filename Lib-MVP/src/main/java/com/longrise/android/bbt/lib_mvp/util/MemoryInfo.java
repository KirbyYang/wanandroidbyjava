package com.longrise.android.bbt.lib_mvp.util;

import android.app.ActivityManager;
import android.content.Context;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by godliness on 2018/12/31.
 * From the BaoBao project
 *
 * @author godliness
 */

public final class MemoryInfo {

    /**
     * Get a MemoryInfo object for the device's current memory status.
     *
     * @param cxt Context
     */
    public static ActivityManager.MemoryInfo getAvailableMemory(Context cxt) {
        ActivityManager activityManager = (ActivityManager) cxt.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }
}
