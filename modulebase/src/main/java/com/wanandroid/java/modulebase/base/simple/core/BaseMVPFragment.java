package com.wanandroid.java.modulebase.base.simple.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.wanandroid.java.modulebase.BuildConfig;
import com.wanandroid.java.modulebase.base.simple.core.loadstyle.LoadingStyle2;
import com.wanandroid.java.modulebase.base.simple.core.loadstyle.base.IResetLoadListener;
import com.wanandroid.java.modulebase.base.simple.core.mvp.BasePresenter;
import com.wanandroid.java.modulebase.base.simple.core.mvp.BaseView;
import com.wanandroid.java.modulebase.base.util.GenericUtil;
import com.wanandroid.java.modulebase.base.util.NetUtil;

/**
 * Created by godliness on 2018/12/31.
 * From the BaoBao project
 *
 * @author godliness
 *         管理Fragment中MVP结构创建
 */

public abstract class BaseMVPFragment<P extends BasePresenter> extends Fragment implements IResetLoadListener {

    private static final boolean DEBUG = BuildConfig.DEBUG;

    /**
     * @see BasePresenter
     */
    public P mPresenter;
    /**
     * 当前Fragment是否已经destroy
     */
    private boolean mDestroy;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = GenericUtil.getT(this, 0);
        if (mPresenter != null && this instanceof BaseView) {
            mPresenter.attachTarget(getContext());
            mPresenter.attachV(this);
        }
        mDestroy = false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.init();
        }
    }

    @Override
    public void onLoadReset() {
        Toast.makeText(getContext().getApplicationContext(), "please override onLoadReset()", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public final <T> T findViewById2(int resId) {
        View view = getView();
        if (view != null) {
            return (T) view.findViewById(resId);
        }
        return null;
    }

    /**
     * 是否需要加载样式
     */
    protected boolean hasLoadStyle() {
        return true;
    }

    /**
     * 开始加载
     *
     * @see BaseView
     */
    public void showLoadingDialog() {
        if (hasLoadStyle() && isHealthState()) {
            LoadingStyle2.loadingStart(this);
        } else {
            if (DEBUG) {
                illegalStateLoadStyle();
            }
        }
    }

    /**
     * 加载出错
     *
     * @see BaseView
     */
    public void showLoadingError() {
        if (hasLoadStyle()) {
            if (NetUtil.isNetWorkEnable(getContext())) {
                LoadingStyle2.loadingError(this);
            } else {
                LoadingStyle2.loadingNoneNetWork(this);
            }
        } else {
            if (DEBUG) {
                illegalStateLoadStyle();
            }
        }
    }

    /**
     * 加载为空
     *
     * @see BaseView
     */
    public void showLoadingEmpty() {
        if (hasLoadStyle()) {
            LoadingStyle2.loadingEmpty(this);
        } else {
            if (DEBUG) {
                illegalStateLoadStyle();
            }
        }
    }

    /**
     * 加载完成
     *
     * @see BaseView
     */
    public void dismissLoadingDialog() {
        if (hasLoadStyle()) {
            LoadingStyle2.loadingNormal(this);
        } else {
            if (DEBUG) {
                illegalStateLoadStyle();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachTarget();
        }
        if (hasLoadStyle()) {
            LoadingStyle2.onDestroy(this);
        }
        mDestroy = true;
        super.onDestroy();
    }

    /**
     * 当前Fragment是否处于健康状态
     */
    public final boolean isHealthState() {
        Context cxt = getContext();
        if (cxt instanceof BaseSuperActivity) {
            return ((BaseSuperActivity) cxt).isHealthState();
        }
        return !mDestroy;
    }

    private void illegalStateLoadStyle() {
        if (!hasLoadStyle()) {
            throw new RuntimeException("hasLoadStyle() must return true or current instance is destroy");
        }
    }
}
