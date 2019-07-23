package com.wanandroid.java.modulebase.base.assist;

import android.content.ComponentCallbacks2;

import java.util.LinkedList;

/**
 * Created by godliness on 2019/1/7.
 * From the BaoBao project
 *
 * @author godliness
 *         辅助Fragment完成懒加载
 */

public final class LazyLoad {

    private static final LinkedList<LazyLoad> LAZY_LOADS = new LinkedList<>();

    private static final int MAX_LAZY_SIZE = 3;

    public boolean mViewCreated;
    public boolean mUserVisibled;
    public boolean mLoaded;

    private LazyLoad() {

    }

    /**
     * 获取FragmentLazyLoad实例
     */
    public static LazyLoad getLazyLoad() {
        if (LAZY_LOADS.size() > 0) {
            LazyLoad lazyLoad = LAZY_LOADS.removeFirst();
            lazyLoad.initState();
            return lazyLoad;
        }
        return new LazyLoad();
    }

    /**
     * 回收FragmentLazyLoad
     */
    public static void recycle(LazyLoad lazyLoad) {
        if (LAZY_LOADS.size() < MAX_LAZY_SIZE) {
            LAZY_LOADS.add(lazyLoad);
        }
    }

    /**
     * 清理LoadStyle缓存
     *
     * @param level {@link ComponentCallbacks2}
     */
    public static void clearLazyLoad(int level) {
        switch (level) {
            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:
                LAZY_LOADS.clear();
                break;

            default:
                break;
        }
    }

    private void initState() {
        mViewCreated = false;
        mUserVisibled = false;
        mLoaded = false;
    }
}
