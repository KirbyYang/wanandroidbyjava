package com.wanandroid.java.modulebase.base.simple.core.mvp;

import android.content.Context;


import com.wanandroid.java.modulebase.base.http.RetrofitClient;
import com.wanandroid.java.modulebase.base.rx.BaseObserver;
import com.wanandroid.java.modulebase.base.simple.core.BaseMVPActivity;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

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

    // 管理订阅关系，用于取消订阅
    protected CompositeDisposable compositeDisposable;


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

    /**
     * 添加订阅
     */
    public void addSubscribe(Observable<?> observable, BaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        BaseObserver baseObserver = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
        compositeDisposable.add(baseObserver);
    }

    /**
     * 取消订阅
     */
    public void unsubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    protected <T> T create(Class<T> clazz) {
        return RetrofitClient.getInstance().getRetrofit().create(clazz);
    }

}
