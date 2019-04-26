package com.wanandroid.java.base.cache;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.os.Bundle;


import com.wanandroid.java.base.common.StrictConsts;

import java.util.LinkedList;

/**
 * Created by godliness on 2018/12/31.
 * From the BaoBao project
 *
 * @author godliness
 *         Bundle复用池
 */

public final class BundlePool {

    private static final String TAG = "BundlePool";

    public static final String EXTRA_FROM = "bundle_pool_extra_form";

    private static final LinkedList<Bundle> BUNDLES = new LinkedList<>();

    /**
     * 获得可复用的Bundle对象
     */
    public static Bundle obtion() {
        synchronized (BUNDLES) {
            Bundle extra = null;
            if (BUNDLES.size() > 0) {
                extra = BUNDLES.removeFirst();
            }
            if (extra == null) {
                extra = new Bundle();
            }
            if (StrictConsts.STRICT) {
                extra.putBoolean(EXTRA_FROM, true);
            }
            return extra;
        }
    }

    /**
     * 回收Bundle实例{@link  BaseMVPActivity#startActivityForResult(Intent, int, Bundle)}
     *
     * @param extra Bundle
     */
    public static void recycle(Bundle extra) {
        synchronized (BUNDLES) {
            if (BUNDLES.size() < 1) {
                extra.clear();
                BUNDLES.add(extra);
            }
        }
    }

    /**
     * 回收Bundle实例{@link com.longrise.android.bbt.lib_mvp.base.BaseMVPActivity#startActivityForResult(Intent, int, Bundle)}
     */
    public static void recycle(Intent intent) {
        if (StrictConsts.STRICT) {
            Bundle extra = intent.getExtras();
            if (extra != null) {
                recycle(extra);
            }
        }
    }

    /**
     * 清理Bundle缓存
     *
     * @param level {@link ComponentCallbacks2}
     */
    public static void clearBundle(int level) {
        switch (level) {
            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:
                BUNDLES.clear();
                break;

            default:
                break;
        }
    }
}
