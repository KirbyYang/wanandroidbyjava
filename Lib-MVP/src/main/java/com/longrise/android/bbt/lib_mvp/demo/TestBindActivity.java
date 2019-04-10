package com.longrise.android.bbt.lib_mvp.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.longrise.android.bbt.lib_mvp.BaseBindActivity;
import com.longrise.android.bbt.lib_mvp.R;
import com.longrise.android.bbt.lib_mvp.demo.bindview.TestBindView;

/**
 * Created by godliness on 2019/1/13.
 * From the BaoBao project
 *
 * @author godliness
 *         测试BaseBindActivity，BindView功能
 */

public class TestBindActivity extends BaseBindActivity<TestPresenter, TestBindView> implements TestView, View.OnClickListener {

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
     * 推荐在该方法完成相关事件绑定，在{@link #initView()}之后回调，自动维护
     *
     * @param event true注册，false反注册
     */
    @Override
    protected void regEvent(boolean event) {

    }

    /**
     * 初始化操作
     */
    @Override
    protected void init() {
        mPresenter.requestData();
    }

    @Override
    protected int customTitleLayout() {
        return R.layout.lib_mvp_custom_title_layout;
    }

    /**
     * 返回当前页面标题
     */
    @Override
    protected int hasTitle() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.recycle) {
            Toast.makeText(this, "recyle", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.leak) {
            Toast.makeText(this, "leak", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPermissionsResult(int requestCode, boolean result) {
        if (result) {
            Toast.makeText(this, "权限申请通过", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "权限申请失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void notResponce() {

    }
}
