package com.wanandroid.java.base.simple.core.loadstyle.impl;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wanandroid.java.R;
import com.wanandroid.java.base.simple.core.loadstyle.base.BaseLoadStyle2;
import com.wanandroid.java.base.util.MvpLog;

/**
 * Created by godliness on 2019/1/10.
 * From the BaoBao project
 *
 * @author godliness
 *         Activity加载样式
 */

public final class ActivityLoadStyle extends BaseLoadStyle2<Activity> {

    private static final String TAG = "ActivityLoadStyle";

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
            mLoadView = inflaterLoadStyle(contentView, R.layout.lib_mvp_loading_activity_load_style);
        }
        bindLoadStyle(contentView, mLoadView);
    }

    @Override
    public void loadingError(ViewGroup contentView, int resId, int textId) {
        removeAllLoadStyle();
        if (mErrorView == null) {
            mErrorView = inflaterLoadStyle(contentView, R.layout.lib_mvp_error_activity_load_style);
            mErrorView.setOnClickListener(mClick);
        }
        bindImageResource(mErrorView, R.id.iv_error_activity_load_style_lib_mvp, resId);
        bindTextResource(mErrorView, R.id.tv_error_activity_load_style_lib_mvp, textId);
        TextView resetView = findViewById(mErrorView, R.id.view_reset_load_style);
        if (resetView != null && !resetView.hasOnClickListeners()) {
            resetView.setOnClickListener(this);
        }
        bindLoadStyle(contentView, mErrorView);
    }

    @Override
    public void loadingEmpty(ViewGroup contentView, int resId, int textId) {
        removeAllLoadStyle();
        if (mEmptyView == null) {
            mEmptyView = inflaterLoadStyle(contentView, R.layout.lib_mvp_empty_activity_load_style);
            mEmptyView.setOnClickListener(mClick);
        }
        bindImageResource(mEmptyView, R.id.iv_empty_activity_load_style_lib_mvp, resId);
        bindTextResource(mEmptyView, R.id.tv_empty_activity_load_style_lib_mvp, textId);
        bindLoadStyle(contentView, mEmptyView);
    }

    @Override
    public void loadingNetWork(ViewGroup contentView, int resId, int textId) {
        removeAllLoadStyle();
        if (mNetWorkView == null) {
            mNetWorkView = inflaterLoadStyle(contentView, R.layout.lib_mvp_no_network_activity_load_style);
            mNetWorkView.setOnClickListener(mClick);
        }
        bindImageResource(mNetWorkView, R.id.iv_network_activity_load_style_lib_mvp, resId);
        bindTextResource(mNetWorkView, R.id.tv_network_activity_load_style_lib_mvp, textId);
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
    public void removeTreeView() {
        removeAllLoadStyle();
    }

    @Override
    public void release() {
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
