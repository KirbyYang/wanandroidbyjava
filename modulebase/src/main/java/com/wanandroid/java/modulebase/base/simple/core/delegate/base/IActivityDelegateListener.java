package com.wanandroid.java.modulebase.base.simple.core.delegate.base;

/**
 * Created by godliness on 2019/1/13.
 * From the BaoBao project
 *
 * @author godliness
 *         ActivityDelegate2相关回调
 */

public interface IActivityDelegateListener {

    /**
     * ActivityDelegate中点击back事件
     */
    void delegateBackPressed();

    /**
     * ActivityDelegate右侧文字按钮
     */
    void delegateRightText();

    /**
     * ActivityDelegate右侧icon按钮
     */
    void delegateRightIcon();
}
