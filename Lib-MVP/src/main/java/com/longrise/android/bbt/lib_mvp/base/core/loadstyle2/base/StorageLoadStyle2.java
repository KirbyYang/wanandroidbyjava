package com.longrise.android.bbt.lib_mvp.base.core.loadstyle2.base;

import android.content.ComponentCallbacks2;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.longrise.android.bbt.lib_mvp.util.MvpLog;

import java.util.LinkedList;

/**
 * Created by godliness on 2019/1/11.
 * From the BaoBao project
 *
 * @author godliness
 *         LoadStyle生命周期管理
 */

public class StorageLoadStyle2 {

    private static final String TAG = "StorageLoadStyle2";

    private static final int MAX_LOAD_STYLE_SIZE = 3;

    private static final ArrayMap<Class<? extends BaseLoadStyle2>, LinkedList<BaseLoadStyle2>> LOAD_STYLE_MAP = new ArrayMap<>();

    /**
     * 获取LoadStyle实例
     *
     * @param clz    ? extends ActivityBaseLoadStyle or ? extends FragmentBaseLoadStyle
     * @param params ?参数
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseLoadStyle2, P> T obtion(Class<T> clz, P params) {
        synchronized (LOAD_STYLE_MAP) {
            LinkedList<BaseLoadStyle2> linkedList = LOAD_STYLE_MAP.get(clz);
            if (linkedList == null) {
                LOAD_STYLE_MAP.put(clz, linkedList = new LinkedList<>());
            }
            T loadStyle = null;
            if (linkedList.size() > 0) {
                loadStyle = (T) linkedList.removeFirst();
            }
            if (loadStyle == null) {
                //reflection load instance
                loadStyle = reflectloadStyle(clz, params);
            }
            if (loadStyle != null) {
                loadStyle.attachUser(params);
            }
            return loadStyle;
        }
    }

    /**
     * 回收LoadStyle实例
     *
     * @param loadStyle 加载样式
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseLoadStyle2> void recycle(@NonNull T loadStyle) {
        synchronized (LOAD_STYLE_MAP) {
            LinkedList<BaseLoadStyle2> linkedList = LOAD_STYLE_MAP.get(loadStyle.getClass());
            if (linkedList == null) {
                LOAD_STYLE_MAP.put(loadStyle.getClass(), linkedList = new LinkedList<>());
            }
            if (linkedList.size() < MAX_LOAD_STYLE_SIZE) {
                linkedList.add(loadStyle);
            }
            //remove view parent
            loadStyle.detachUser();
        }
    }

    /**
     * 清理LoadStyle缓存
     *
     * @param level {@link ComponentCallbacks2}
     */
    public static void clearLoadStyle(int level) {
        switch (level) {
            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
                //不做任何操作
                break;

            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:
                //释放加载样式中View资源，使其成为不占内存的空壳
                int low = LOAD_STYLE_MAP.size();
                for (int i = 0; i < low; i++) {
                    LinkedList<BaseLoadStyle2> linkedList = LOAD_STYLE_MAP.valueAt(i);
                    BaseLoadStyle2 loadStyle2;
                    while ((loadStyle2 = linkedList.poll()) != null) {
                        loadStyle2.release();
                    }
                }
                MvpLog.e(TAG, "释放加载样式View资源");
                break;

            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
                //每种加载样式仅保留一条
                int moderate = LOAD_STYLE_MAP.size();
                for (int i = 0; i < moderate; i++) {
                    LinkedList<BaseLoadStyle2> linkedList = LOAD_STYLE_MAP.valueAt(i);
                    int linkSize = linkedList.size();
                    if (linkSize > 1) {
                        BaseLoadStyle2 loadStyle2 = linkedList.removeFirst();
                        linkedList.clear();
                        linkedList.add(loadStyle2);
                    }
                }
                MvpLog.e(TAG, "每种加载样式，仅保留一条");
                break;
            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:
                //释放全部
                LOAD_STYLE_MAP.clear();
                MvpLog.e(TAG, "释放全部加载样式");
                break;

            default:
                break;
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends BaseLoadStyle2, P> T reflectloadStyle(Class<T> clz, P params) {
        try {
            return clz.newInstance();
        } catch (Exception ignore) {
            MvpLog.print(ignore);
        }
        return null;
    }
}
