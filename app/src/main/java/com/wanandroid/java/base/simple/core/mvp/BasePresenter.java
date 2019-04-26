package com.wanandroid.java.base.simple.core.mvp;

import android.content.Context;


import java.lang.ref.WeakReference;

/**
 * Created by godliness on 2018/12/30.
 * From the BaoBao project
 *
 * @author godliness
 *         MVP结构中Presenter
 */

public abstract class BasePresenter<V> {

    /**
     * {@link #isFinishing()}
     */
    private WeakReference<Context> mContext;
    /**
     * {@link #isFinishing()}
     *
     * @see BaseView
     */
    private WeakReference<V> mView;
    /**
     * 当前依附的Activity是否已经finish
     */
    private boolean mFinish;

    protected BasePresenter() {
    }

    /**
     * 该方法在{@link BaseMVPActivity#initView()#init()}之后回调
     */
    public abstract void init();

    /**
     * 在Presenter申请的资源应该在这里释放
     */
    public abstract void onDestroy();

    /**
     * {@link #isFinishing()}
     */
    public final V getV() {
        return mView != null ? mView.get() : null;
    }

    /**
     * {@link BaseMVPActivity#initMVPFrame()#attachContext(Context)}
     */
    public final void attachTarget(Context cxt) {
        this.mContext = new WeakReference<>(cxt);
    }

    /**
     * 获取当前依附的上下文，有可能获取到为null
     */
    public final Context getContext() {
        return mContext != null ? mContext.get() : null;
    }

    /**
     * {@link BaseMVPActivity#initMVPFrame()#attachV(Object)}
     */
    public final void attachV(V v) {
        this.mView = new WeakReference<>(v);
    }

    /**
     * {@link BaseMVPActivity#onDestroy()#notifyFinish()}
     */
    public final void detachTarget() {
        if (mFinish) {
            return;
        }
        mFinish = true;
        onDestroy();
        detachV();
    }

    /**
     * 判断当前依附的Activity是否已经finish()
     */
    public final boolean isFinishing() {
        return mFinish;
    }

    private void detachV() {
    }

}
