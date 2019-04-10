package com.longrise.android.bbt.lib_mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.longrise.android.bbt.lib_mvp.base.core.bindview.BaseBindView;
import com.longrise.android.bbt.lib_mvp.base.core.mvp.BasePresenter;
import com.longrise.android.bbt.lib_mvp.util.GenericUtil;

/**
 * Created by godliness on 2019/1/9.
 * From the BaoBao project
 *
 * @author godliness
 *         对BaseFragment进行扩展
 *         将View相关逻辑抽离{@link BaseBindView}简化Fragment
 */

public abstract class BaseBindFragment<P extends BasePresenter, BindView extends BaseBindView> extends BaseFragment<P> {

    /**
     * @see BaseBindView
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

    /**
     * 初始化操作
     */
    protected abstract void init();

    @Override
    public void onResume() {
        super.onResume();
        if (mBindView != null) {
            mBindView.onResume();
        }
    }

    @Override
    public void onPause() {
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
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (mBindView != null) {
            mBindView.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onDestroy() {
        if (mBindView != null) {
            mBindView.detachTarget();
        }
        super.onDestroy();
    }
}
