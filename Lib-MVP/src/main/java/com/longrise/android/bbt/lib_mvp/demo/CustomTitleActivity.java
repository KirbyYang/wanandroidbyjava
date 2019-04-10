package com.longrise.android.bbt.lib_mvp.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.longrise.android.bbt.lib_mvp.BaseActivity;
import com.longrise.android.bbt.lib_mvp.R;

/**
 * Created by godliness on 2019/1/12.
 * From the BaoBao project
 *
 * @author godliness
 *         自定义标题栏 测试BaseActivity的自定义标题栏功能
 */

public class CustomTitleActivity extends BaseActivity<TestPresenter> implements View.OnClickListener {

    private static final String TAG = "CustomTitleActivity";

    private TextView mTitle;

    /**
     * 返回当前Activity的xml布局资源
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected int getContentViewId(Bundle savedInstanceState) {
        return R.layout.lib_mvp_activity_custom_title;
    }

    /**
     * 初始化View
     */
    @Override
    protected void initView() {
        mTitle = findViewById2(R.id.title);
        mTitle.setText("自定义标题栏文字");
    }

    @Override
    protected int customTitleLayout() {
        return R.layout.lib_mvp_custom_title_layout;
    }

    /**
     * 返回当前页面标题,如果是{@link #customTitleLayout()}返回默认值即可
     */
    @Override
    protected int hasTitle() {
        return 0;
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
    }

    @Override
    public void onClick(View v) {

    }
}
