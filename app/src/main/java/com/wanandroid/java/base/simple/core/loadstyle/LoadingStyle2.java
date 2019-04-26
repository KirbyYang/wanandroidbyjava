package com.wanandroid.java.base.simple.core.loadstyle;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.ViewGroup;

import com.wanandroid.java.base.BaseActivity;
import com.wanandroid.java.base.BaseFragment;
import com.wanandroid.java.base.assist.LazyLoad;
import com.wanandroid.java.base.simple.core.delegate.ActivityDelegate2;
import com.wanandroid.java.base.simple.core.loadstyle.base.BaseLoadStyle2;
import com.wanandroid.java.base.simple.core.loadstyle.base.StorageLoadStyle2;
import com.wanandroid.java.base.simple.core.loadstyle.impl.ActivityLoadStyle;
import com.wanandroid.java.base.util.ObjectUtil;

/**
 * Created by godliness on 2019/1/11.
 * From the BaoBao project
 *
 * @author godliness
 *         加载样式统一入口
 *         <p>
 *         LoadingStyle2仅对{@link BaseActivity}和{@link BaseFragment}统一约束加载样式
 */

public class LoadingStyle2 {

    private static final ArrayMap<Integer, BaseLoadStyle2> LOAD_STYLE_ARRAY_MAP = new ArrayMap<>();
    private static final int RESOURCE = -1;

    /**
     * 自行决定对{@link ViewGroup}添加加载样式
     */
    public static <T> BaseLoadStyle2 findLoadStyle(T target) {
        BaseLoadStyle2 loadStyle = LOAD_STYLE_ARRAY_MAP.get(ObjectUtil.hashCode(target));
        if (loadStyle == null) {
            loadStyle = StorageLoadStyle2.obtion(getLoadStyleImpl(target), target);
            LOAD_STYLE_ARRAY_MAP.put(ObjectUtil.hashCode(target), loadStyle);
        }
        return loadStyle;
    }

    /**
     * 开始加载，仅对{@link BaseActivity}{@link BaseFragment}有效
     *
     * @see BaseActivity
     * @see BaseFragment
     */
    public static <T> void loadingStart(T target) {
        BaseLoadStyle2 loadStyle = findLoadStyle(target);
        if (loadStyle != null) {
            loadStyle.loadingStart(getUser(target));
        }
    }

    /**
     * 加载出错，仅对{@link BaseActivity}{@link BaseFragment}有效
     *
     * @see BaseActivity
     * @see BaseFragment
     */
    public static <T> void loadingError(T target) {
        BaseLoadStyle2 loadStyle = LOAD_STYLE_ARRAY_MAP.get(ObjectUtil.hashCode(target));
        if (loadStyle != null) {
            loadStyle.loadingError(getUser(target), RESOURCE, RESOURCE);
        }
    }

    /**
     * 无网络连接，仅对{@link BaseActivity}{@link BaseFragment}有效
     *
     * @see BaseActivity
     * @see BaseFragment
     */
    public static <T> void loadingNoneNetWork(T target) {
        BaseLoadStyle2 loadStyle2 = LOAD_STYLE_ARRAY_MAP.get(ObjectUtil.hashCode(target));
        if (loadStyle2 != null) {
            loadStyle2.loadingNetWork(getUser(target), RESOURCE, RESOURCE);
        }
    }

    /**
     * 加载为空，仅对{@link BaseActivity}{@link BaseFragment}有效
     *
     * @see BaseActivity
     * @see BaseFragment
     */
    public static <T> void loadingEmpty(T target) {
        BaseLoadStyle2 loadStyle = LOAD_STYLE_ARRAY_MAP.get(ObjectUtil.hashCode(target));
        if (loadStyle != null) {
            loadStyle.loadingEmpty(getUser(target), RESOURCE, RESOURCE);
        }
    }

    /**
     * 加载完成，仅对{@link BaseActivity}{@link BaseFragment}有效
     *
     * @see BaseActivity
     * @see BaseFragment
     */
    public static <T> void loadingNormal(T target) {
        BaseLoadStyle2 loadStyle = LOAD_STYLE_ARRAY_MAP.get(ObjectUtil.hashCode(target));
        if (loadStyle != null) {
            loadStyle.loadingNormal();
        }
    }

    /**
     * 加载生命周期结束
     */
    public static <T> void onDestroy(T target) {
        BaseLoadStyle2 loadStyle = LOAD_STYLE_ARRAY_MAP.remove(ObjectUtil.hashCode(target));
        if (loadStyle != null) {
            StorageLoadStyle2.recycle(loadStyle);
        }
    }

    private static <T> Class getLoadStyleImpl(T target) {
        if (target instanceof Activity) {
            return ActivityLoadStyle.class;
        }
        if (target instanceof Fragment) {
            return LazyLoad.class;
        }
        return null;
    }

    private static <T> ViewGroup getUser(T target) {
        if (target instanceof BaseActivity) {
            return (ViewGroup) ((BaseActivity) target).findViewById2(ActivityDelegate2.ID_DELEGATE_CONTENT);
        }
        if (target instanceof BaseFragment) {
            return (ViewGroup) ((BaseFragment) target).getView();
        }
        return null;
    }
}