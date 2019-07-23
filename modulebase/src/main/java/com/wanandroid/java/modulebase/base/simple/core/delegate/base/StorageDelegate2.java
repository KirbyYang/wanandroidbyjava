package com.wanandroid.java.modulebase.base.simple.core.delegate.base;

import android.content.ComponentCallbacks2;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;


import com.wanandroid.java.modulebase.base.util.MvpLog;

import java.util.LinkedList;

/**
 * Created by godliness on 2019/1/13.
 * From the BaoBao project
 *
 * @author godliness
 *         Delegate生命周期管理
 */

public final class StorageDelegate2 {

    private static final String TAG = "StorageDelegate2";

    private static final int MAX_DELEGATE_SIZE = 3;

    private static final ArrayMap<Class<? extends BaseDelegate2>, LinkedList<BaseDelegate2>> DELEGATE_MAP = new ArrayMap<>();

    /**
     * 尝试从缓存池中获取Delegate
     */
    @SuppressWarnings("unchecked")
    static <T extends BaseDelegate2, P> T obtion(Class<T> clz, P params) {
        synchronized (DELEGATE_MAP) {
            LinkedList<BaseDelegate2> linkedList = DELEGATE_MAP.get(clz);
            if (linkedList == null) {
                DELEGATE_MAP.put(clz, linkedList = new LinkedList<>());
            }
            T delegate = null;
            if (linkedList.size() > 0) {
                //reuse delegate
                delegate = (T) linkedList.removeFirst();
            }
            if (delegate == null) {
                //reflex load delegate instance
                delegate = loadDelegate(clz, params);
            }
            if (delegate != null) {
                delegate.attachDelegate(params);
            }
            return delegate;
        }
    }

    /**
     * 尝试回收当前Delegate
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseDelegate2> void recycle(@NonNull T delegate) {
        synchronized (DELEGATE_MAP) {
            LinkedList<BaseDelegate2> linkedList = DELEGATE_MAP.get(delegate.getClass());
            if (linkedList == null) {
                DELEGATE_MAP.put(delegate.getClass(), linkedList = new LinkedList<>());
            }
            delegate.detachDelegate();
            if (linkedList.size() < MAX_DELEGATE_SIZE) {
                //recover init
                linkedList.add(delegate);
            }
            //do nothing, Direct discard
        }
    }

    /**
     * 清理Delegate缓存
     *
     * @param level {@link ComponentCallbacks2}
     */
    public static void clearDelegate(int level) {
        switch (level) {
            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
                //不做任何操作
                break;

            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:
                //释放全部Delegate中View资源使其成为不占内存的空壳
                int low = DELEGATE_MAP.size();
                for (int i = 0; i < low; i++) {
                    LinkedList<BaseDelegate2> linkedList = DELEGATE_MAP.valueAt(i);
                    BaseDelegate2 delegate2;
                    while ((delegate2 = linkedList.poll()) != null) {
                        delegate2.release();
                    }
                }
                MvpLog.e(TAG, "释放Delegate中View资源");
                break;


            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
                //每种类型的Delegate类型实例仅保留一条
                int moderate = DELEGATE_MAP.size();
                for (int i = 0; i < moderate; i++) {
                    LinkedList<BaseDelegate2> linkedList = DELEGATE_MAP.valueAt(i);
                    int size = linkedList.size();
                    if (size > 1) {
                        BaseDelegate2 delegate2 = linkedList.removeFirst();
                        linkedList.clear();
                        linkedList.add(delegate2);
                    }
                }
                MvpLog.e(TAG, "每种Delegate类型实例仅保留一条");
                break;

            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:
                //释放全部Delegate
                DELEGATE_MAP.clear();
                MvpLog.e(TAG, "释放全部Delegate");
                break;

            default:
                break;
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends BaseDelegate2, P> T loadDelegate(Class<T> clz, P params) {
        try {
            return clz.newInstance();
        } catch (Exception ignore) {
            MvpLog.print(ignore);
        }
        return null;
    }
}
