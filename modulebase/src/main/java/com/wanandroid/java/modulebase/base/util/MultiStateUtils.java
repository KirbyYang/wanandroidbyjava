package com.wanandroid.java.modulebase.base.util;

import android.view.View;

import com.kennyc.view.MultiStateView;
import com.wanandroid.java.modulebase.base.listener.OnClickListener2;
import com.wanandroid.java.modulebase.base.listener.SimpleListener;

/**
 * Created by YZH on 2019/7/19.
 * From the BaoBao project
 *
 * @author YZH
 */
public class MultiStateUtils {

    public static void toLoading(MultiStateView view){
        view.setViewState(MultiStateView.VIEW_STATE_LOADING);
    }

    public static void toEmpty(MultiStateView view){
        view.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    public static void toError(MultiStateView view){
        view.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    public static void toContent(MultiStateView view){
        view.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    public static void setEmptyAndErrorClick(MultiStateView view, SimpleListener listener){
        setEmptyClick(view, listener);
        setErrorClick(view, listener);
    }

    public static void setEmptyClick(MultiStateView view, final SimpleListener listener){
        View empty = view.getView(MultiStateView.VIEW_STATE_EMPTY);
        if (empty != null) {
            empty.setOnClickListener(new OnClickListener2() {
                @Override
                public void onClick2(View v) {
                    listener.onResult();
                }
            });
        }
    }

    public static void setErrorClick(MultiStateView view, final SimpleListener listener){
        View error = view.getView(MultiStateView.VIEW_STATE_ERROR);
        if (error != null) {
            error.setOnClickListener(new OnClickListener2() {
                @Override
                public void onClick2(View v) {
                    listener.onResult();
                }
            });
        }
    }
}
