package com.wanandroid.java.modulebase.base.listener;

import android.view.View;

import com.wanandroid.java.modulebase.base.util.ClickHelper;

/**
 * Created by YZH on 2019/7/19.
 * From the BaoBao project
 *
 * @author YZH
 */
public abstract class OnClickListener2 implements View.OnClickListener {
    @Override
    public final void onClick(final View v) {
        ClickHelper.onlyFirstSameView(v, new ClickHelper.Callback() {
            @Override
            public void onClick(View view) {
                onClick2(view);
            }
        });
    }

    public abstract void onClick2(View v);
}
