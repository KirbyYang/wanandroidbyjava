package com.wanandroid.java.modulebase.base.util;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YZH on 2019/7/19.
 * From the BaoBao project
 *
 * @author YZH
 */
public class ClickHelper {

    private static long DELAY = 500L;
    private static long lastTime = 0L;
    private static List<Integer> viewIds = null;
    private static final int SAME_VIEW_SIZE = 10;

    private ClickHelper() {
    }

    public static void setDelay(long delay) {
        ClickHelper.DELAY = delay;
    }

    public static long getDelay() {
        return DELAY;
    }

    public static void onlyFirstIgnoreView(final View target, @NonNull final Callback callback) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - lastTime > DELAY) {
            callback.onClick(target);
            lastTime = nowTime;
        }
    }

    public static void onlyFirstSameView(final View target, @NonNull final Callback callback) {
        long nowTime = System.currentTimeMillis();
        int id = target.getId();
        if (viewIds == null) {
            viewIds = new ArrayList<>(SAME_VIEW_SIZE);
        }
        if (viewIds.contains(id)) {
            if (nowTime - lastTime > DELAY) {
                callback.onClick(target);
                lastTime = nowTime;
            }
        } else {
            if (viewIds.size() >= SAME_VIEW_SIZE) {
                viewIds.remove(0);
            }
            viewIds.add(id);
            callback.onClick(target);
            lastTime = nowTime;
        }
    }

    public interface Callback {
        void onClick(View view);
    }
}

