package com.wanandroid.java.base.simple.core.loadstyle.impl;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.wanandroid.java.R;
import com.wanandroid.java.base.simple.core.loadstyle.base.BaseLoadStyle2;
import com.wanandroid.java.base.util.MvpLog;


/**
 * Created by godliness on 2019/1/11.
 * From the BaoBao project
 *
 * @author godliness
 *         Fragment加载样式
 */

public final class FragmentLoadStyle extends BaseLoadStyle2<Fragment> {

    private static final String TAG = "FragmentLoadStyle";

    private View mLoadView;
    private View mErrorView;
    private View mNetWorkView;
    private View mEmptyView;

    private View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //拦截事件
        }
    };

    @Override
    public void loadingStart(ViewGroup contentView) {
        removeAllLoadStyle();
        if (mLoadView == null) {
            mLoadView = inflaterLoadStyle(contentView, R.layout.lib_mvp_loading_fragment_load_style);
        }
        bindLoadStyle(contentView, mLoadView);
    }

    @Override
    public void loadingError(ViewGroup contentView, int resId, int textId) {
        removeAllLoadStyle();
        if (mErrorView == null) {
            mEmptyView = inflaterLoadStyle(contentView, R.layout.lib_mvp_error_fragment_load_style);
            mEmptyView.setOnClickListener(mClick);
        }
        bindImageResource(mErrorView, R.id.iv_error_fragment_load_style_lib_mvp, DEFAULT_RES);
        bindTextResource(mErrorView, R.id.tv_error_fragment_load_style_lib_mvp, DEFAULT_RES);
        View resetView = findViewById(mErrorView, R.id.view_reset_load_style);
        if (resetView != null && !resetView.hasOnClickListeners()) {
            resetView.setOnClickListener(this);
        }
        bindLoadStyle(contentView, mErrorView);
    }

    @Override
    public void loadingEmpty(ViewGroup contentView, int resId, int textId) {
        removeAllLoadStyle();
        if (mEmptyView == null) {
            mEmptyView = inflaterLoadStyle(contentView, R.layout.lib_mvp_empty_fragment_load_style);
            mEmptyView.setOnClickListener(mClick);
        }
        bindImageResource(mEmptyView, R.id.iv_empty_fragment_load_style_lib_mvp, DEFAULT_RES);
        bindTextResource(mEmptyView, R.id.tv_empty_fragment_load_style_lib_mvp, DEFAULT_RES);
        bindLoadStyle(contentView, mEmptyView);
    }

    @Override
    public void loadingNetWork(ViewGroup contentView, int resId, int textId) {
        removeAllLoadStyle();
        if (mNetWorkView == null) {
            mNetWorkView = inflaterLoadStyle(contentView, R.layout.lib_mvp_no_network_fragment_load_style);
            mNetWorkView.setOnClickListener(mClick);
        }
        bindImageResource(mNetWorkView, R.id.iv_network_fragment_load_style_lib_mvp, DEFAULT_RES);
        bindTextResource(mNetWorkView, R.id.tv_network_fragment_load_style_lib_mvp, DEFAULT_RES);
        View resetView = findViewById(mNetWorkView, R.id.view_reset_load_style);
        if (resetView != null && !resetView.hasOnClickListeners()) {
            resetView.setOnClickListener(this);
        }
        bindLoadStyle(contentView, mNetWorkView);
    }

    @Override
    public void loadingNormal() {
        removeAllLoadStyle();
    }

    @Override
    protected void removeTreeView() {
        removeAllLoadStyle();
    }

    @Override
    protected void release() {
        mLoadView = null;
        mErrorView = null;
        mNetWorkView = null;
        mEmptyView = null;

        MvpLog.e(TAG, "release");
    }

    private void removeAllLoadStyle() {
        removeSelf(mLoadView);
        removeSelf(mErrorView);
        removeSelf(mNetWorkView);
        removeSelf(mEmptyView);
    }
}
