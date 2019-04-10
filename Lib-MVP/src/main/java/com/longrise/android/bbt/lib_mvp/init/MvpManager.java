package com.longrise.android.bbt.lib_mvp.init;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;

import com.longrise.android.bbt.lib_mvp.base.assist.LazyLoad;
import com.longrise.android.bbt.lib_mvp.base.core.delegate.base.StorageDelegate2;
import com.longrise.android.bbt.lib_mvp.base.core.loadstyle2.base.StorageLoadStyle2;
import com.longrise.android.bbt.lib_mvp.cache.BundlePool;
import com.longrise.android.bbt.lib_mvp.cache.Strings;
import com.longrise.android.bbt.lib_mvp.util.MvpLog;

/**
 * Created by godliness on 2019/1/2.
 * From the BaoBao project
 *
 * @author godliness
 *         管理Lib-Mvp库相关缓存资源
 */

public final class MvpManager implements ComponentCallbacks2 {

    private static final String TAG = "LibMvpInit";

    /**
     * 是否已经注册
     */
    public static boolean sRegistered;

    private MvpManager() {
    }

    /**
     * 初始化Lib-MVP库
     */
    public static void init(Application application) {
        if (!sRegistered) {
            application.registerComponentCallbacks(new MvpManager());
            AppContext.register(application);
            sRegistered = true;
        }
    }

    /**
     * 释放所有涉及到<Lib-MVP>库的缓存资源
     *
     * @param level {@link ComponentCallbacks2}
     *              {@link BundlePool}
     *              {@link StorageLoadStyle2}
     *              {@link StorageLoadStyle2}
     *              {@link LazyLoad}
     */
    private void clearStorage(int level) {
        BundlePool.clearBundle(level);
        StorageLoadStyle2.clearLoadStyle(level);
        StorageDelegate2.clearDelegate(level);
        LazyLoad.clearLazyLoad(level);
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            Strings.clear();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        try {
            clearStorage(level);
        } catch (Exception e) {
            MvpLog.print(e);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void onLowMemory() {

    }
}
