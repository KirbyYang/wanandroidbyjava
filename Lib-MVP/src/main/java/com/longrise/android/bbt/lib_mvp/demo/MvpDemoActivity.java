package com.longrise.android.bbt.lib_mvp.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.longrise.android.bbt.lib_mvp.R;
import com.longrise.android.bbt.lib_mvp.base.BaseMVPActivity;
import com.longrise.android.bbt.lib_mvp.base.core.loadstyle2.LoadingStyle2;
import com.longrise.android.bbt.lib_mvp.base.core.loadstyle2.base.BaseLoadStyle2;

/**
 * Created by godliness on 2019/1/17.
 * From the BaoBao project
 *
 * @author godliness
 *         测试BaseMVPActivity
 */

public class MvpDemoActivity extends BaseMVPActivity<TestPresenter> implements TestView, View.OnClickListener {

    private TextView mTitle;
    private FrameLayout mContent;

    private Button mStart;
    private Button mStop;

    /**
     * 返回当前Activity的xml布局资源
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.lib_mvp_activity_test;
    }

    /**
     * 初始化View
     */
    @Override
    protected void initView() {
        mTitle = findViewById2(R.id.loadingStart);
        mContent = findViewById2(R.id.fl_test_load_style);
        mStart = findViewById2(R.id.recycle);
        mStop = findViewById2(R.id.leak);

        mPresenter.requestData();
    }

    /**
     * 推荐在该方法完成相关事件绑定，在{@link #initView()}之后回调，自动维护
     *
     * @param event true注册，false反注册
     */
    @Override
    protected void regEvent(boolean event) {
        if (mTitle != null) {
            mTitle.setOnClickListener(event ? this : null);
        }
        if (mStart != null) {
            mStart.setOnClickListener(event ? this : null);
        }
        if (mStop != null) {
            mStop.setOnClickListener(event ? this : null);
        }
    }

    @Override
    public void notResponce() {

    }

    @Override
    public void showLoadingEmpty() {
        LoadingStyle2.findLoadStyle(this).loadingEmpty(mContent, BaseLoadStyle2.DEFAULT_RES, BaseLoadStyle2.DEFAULT_RES);
    }

    @Override
    public void showLoadingError() {
        LoadingStyle2.findLoadStyle(this).loadingError(mContent, BaseLoadStyle2.DEFAULT_RES, BaseLoadStyle2.DEFAULT_RES);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loadingStart) {
            //do nothing
            LoadingStyle2.findLoadStyle(this).loadingNetWork(mContent, BaseLoadStyle2.DEFAULT_RES, BaseLoadStyle2.DEFAULT_RES);
        } else if (v.getId() == R.id.recycle) {
            LoadingStyle2.findLoadStyle(this).loadingStart(mContent);
        } else if (v.getId() == R.id.leak) {
            LoadingStyle2.findLoadStyle(this).loadingNormal();
        }
    }
}
