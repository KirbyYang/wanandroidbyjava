package com.longrise.android.bbt.lib_mvp.util;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

/**
 * Created by godliness on 2019/1/14.
 * From the BaoBao project
 *
 * @author godliness
 *         负责释放DrawCache资源
 */

public final class ViewRelease {

    /**
     * 释放Window中DrawCache资源
     */
    public static void releaseDrawCache(Window window) {
        if (window != null) {
            releaseDrawCache((ViewGroup) window.getDecorView());
        }
    }

    /**
     * 释放View中DrawCache资源
     */
    public static void releaseDrawCache(ViewGroup viewGroup) {
        if (viewGroup == null) {
            return;
        }
        Drawable background = viewGroup.getBackground();
        if (background != null) {
            background.setCallback(null);
        }
        viewGroup.setBackground(null);
        int size = viewGroup.getChildCount();
        for (int i = 0; i < size; i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof ImageView) {
                Drawable drawable = ((ImageView) childView).getDrawable();
                if (drawable != null) {
                    drawable.setCallback(null);
                }
                ((ImageView) childView).setImageDrawable(null);
            } else if (childView instanceof ViewGroup) {
                releaseDrawCache((ViewGroup) childView);
            } else {
                Drawable viewBackground = childView.getBackground();
                if (viewBackground != null) {
                    viewBackground.setCallback(null);
                }
                childView.setBackground(null);
            }
        }
    }
}
