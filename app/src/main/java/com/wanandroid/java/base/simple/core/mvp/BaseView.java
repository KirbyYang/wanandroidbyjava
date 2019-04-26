package com.wanandroid.java.base.simple.core.mvp;

import com.wanandroid.java.base.simple.core.BaseMVPActivity;

/**
 * Created by godliness on 2018/12/30.
 * From the BaoBao project
 *
 * @author godliness
 *         MVP结构中View
 */

public interface BaseView {

    /**
     * 开始加载
     *
     * @see BaseMVPActivity#hasLoadStyle()
     * @see BaseMVPActivity#showLoadingDialog()
     */
    void showLoadingDialog();

    /**
     * 加载出错
     *
     * @see BaseMVPActivity#hasLoadStyle()
     * @see BaseMVPActivity#showLoadingError()
     */
    void showLoadingError();

    /**
     * 加载为空
     *
     * @see BaseMVPActivity#hasLoadStyle()
     * @see BaseMVPActivity#showLoadingEmpty()
     */
    void showLoadingEmpty();

    /**
     * 加载完成
     *
     * @see BaseMVPActivity#hasLoadStyle()
     * @see BaseMVPActivity#dismissLoadingDialog()
     */
    void dismissLoadingDialog();
}
