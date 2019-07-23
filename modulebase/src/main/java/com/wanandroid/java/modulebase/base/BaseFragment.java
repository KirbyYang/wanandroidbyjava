package com.wanandroid.java.modulebase.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.wanandroid.java.modulebase.base.assist.LazyLoad;
import com.wanandroid.java.modulebase.base.simple.core.BaseMVPActivity;
import com.wanandroid.java.modulebase.base.simple.core.BaseMVPFragment;
import com.wanandroid.java.modulebase.base.simple.core.mvp.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by godliness on 2018/12/31.
 * From the BaoBao project
 *
 * @author godliness
 *         Fragment基类
 */

public abstract class BaseFragment<P extends BasePresenter> extends BaseMVPFragment<P> {

    private View mRootView;

    /**
     * 辅助Fragment完成懒加载
     */
    private final LazyLoad mLazyLoad = LazyLoad.getLazyLoad();

    private Unbinder mUnbinder = null;

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutResource(container, savedInstanceState), container, false);
        }
        //FragmentLoadStyle
        return mRootView = generatorLoadStyleLayout(getContext(), mRootView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mRootView != null) {
            mUnbinder = ButterKnife.bind(this, mRootView);
        }
        initView();
        regEvent(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mLazyLoad != null) {
            mLazyLoad.mViewCreated = true;
        }
        performLoad();
    }

    /**
     * 返回当前布局资源
     *
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     */
    protected abstract int getLayoutResource(ViewGroup container, Bundle savedInstanceState);

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 执行懒加载
     */
    protected abstract void performLazyLoad();

    /**
     * 绑定相关事件
     *
     * @param event true 绑定，false 解绑
     */
    protected abstract void regEvent(boolean event);

    /**
     * 用于替代 {@link #setUserVisibleHint(boolean)}
     *
     * @param isVisibleToUser 是否可见
     */
    protected void userVisibleHint(boolean isVisibleToUser) {

    }

    /**
     * 返回当前Fragment跟视图
     */
    @NonNull
    @Override
    public final View getView() {
        return mRootView;
    }

    /**
     * 返回当前依附的Activity
     */
    @SuppressWarnings("unchecked")
    public final <T extends BaseMVPActivity> T getContext2() {
        return (T) getContext();
    }

    /**
     * 重新设置懒加载，一般当界面需要二次刷新时重置
     */
    protected final void resetLazyLoad() {
        if (mLazyLoad != null) {
            mLazyLoad.mLoaded = false;
        }
    }

    /**
     * @see #userVisibleHint(boolean)
     */
    @Override
    public final void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mLazyLoad != null) {
            mLazyLoad.mUserVisibled = isVisibleToUser;
        }
        userVisibleHint(isVisibleToUser);
        performLoad();
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LazyLoad.recycle(mLazyLoad);
        regEvent(false);
        super.onDestroy();
    }

    private void performLoad() {
        if (mLazyLoad != null) {
            LazyLoad lazyLoad = mLazyLoad;
            if (lazyLoad.mViewCreated && lazyLoad.mUserVisibled && !lazyLoad.mLoaded) {
                performLazyLoad();
                lazyLoad.mLoaded = true;
            }
        }
    }

    private View generatorLoadStyleLayout(Context cxt, View target) {
        if (!hasLoadStyle()) {
            return target;
        }
        if (target instanceof CoordinatorLayout || target instanceof FrameLayout || target instanceof RelativeLayout) {
            return target;
        }
        FrameLayout container = new FrameLayout(cxt);
        container.addView(target, target.getLayoutParams());
        return container;
    }
}
