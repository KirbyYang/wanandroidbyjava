package com.wanandroid.java.base;

import android.os.Bundle;

import com.wanandroid.java.base.simple.core.BaseMVPActivity;
import com.wanandroid.java.base.simple.core.bindview.BaseBindView;
import com.wanandroid.java.base.simple.core.mvp.BasePresenter;
import com.wanandroid.java.base.util.GenericUtil;

/**
 * Created by godliness on 2019/1/9.
 * From the BaoBao project
 *
 * @author godliness
 *         对{@link BaseActivity}进行扩展
 *         1、将View相关逻辑抽离{@link BaseBindView}简化Activity，适合较多View绑定时
 *         2、如果存在特殊界面样式需求{@link BaseMVPActivity}
 */

public abstract class BaseBindActivity<P extends BasePresenter, BindView extends BaseBindView> extends BaseActivity<P> {

    /**
     * {@link BaseBindView}
     */
    protected BindView mBindView;

    @SuppressWarnings("unchecked")
    @Override
    protected final void initView() {
        mBindView = GenericUtil.getT(this, 1);
        if (mBindView != null) {
            mBindView.attachTarget(this);
        }
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBindView != null) {
            mBindView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBindView != null) {
            mBindView.onPause();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mBindView != null) {
            mBindView.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mBindView != null) {
            mBindView.onRestoreInstanceState(savedInstanceState);
        }
    }

    /**
     * 初始化操作
     */
    protected abstract void init();

    @Override
    protected void onDestroy() {
        if (mBindView != null) {
            mBindView.detachTarget();
        }
        super.onDestroy();
    }
}
