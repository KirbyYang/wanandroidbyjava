package com.longrise.android.bbt.lib_mvp.demo.bindview;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.longrise.android.bbt.lib_mvp.BaseBindActivity;
import com.longrise.android.bbt.lib_mvp.R;
import com.longrise.android.bbt.lib_mvp.base.core.bindview.BaseBindView;
import com.longrise.android.bbt.lib_mvp.demo.TestBindActivity;

/**
 * Created by godliness on 2019/1/13.
 * From the BaoBao project
 *
 * @author godliness
 *         BaseBindView的使用用例
 */

public class TestBindView extends BaseBindView<TestBindActivity> implements View.OnClickListener {

    private Button mRecycle;
    private Button mLeak;

    private FrameLayout mContent;

    /**
     * 绑定View
     */
    @Override
    protected void bindView() {
        mRecycle = findViewById(R.id.recycle);
        mLeak = findViewById(R.id.leak);
        mContent = findViewById(R.id.fl_test_load_style);
    }

    /**
     * 推荐在该方法中绑定相关事件
     *
     * @param event true 绑定，false 解绑，自动维护
     */
    @Override
    public void regEvent(boolean event) {
        if (mRecycle != null) {
            mRecycle.setOnClickListener(event ? this : null);
        }
        if (mLeak != null) {
            mLeak.setOnClickListener(event ? this : null);
        }
    }

    /**
     * @see BaseBindActivity#onDestroy()
     */
    @Override
    protected void onDestroy() {

    }

}
