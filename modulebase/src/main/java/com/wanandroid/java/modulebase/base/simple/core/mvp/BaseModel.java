package com.wanandroid.java.modulebase.base.simple.core.mvp;

/**
 * Created by godliness on 2018/12/30.
 * From the BaoBao project
 *
 * @author godliness
 *         MVP结构中的M
 */

public interface BaseModel {

    /**
     * BaseModel初始化
     */
    void init();

    /**
     * 默认请求数据
     *
     * @param listener 响应回调
     */
    void loadData(OnLoadListener listener);

    /**
     * 请求响应回调
     */
    interface OnLoadListener<P> {

        /**
         * 响应成功
         *
         * @param params 响应结果 根据泛型结果决定
         */
        void onComplete(P params);

        /**
         * 响应失败
         *
         * @param desc 失败说明
         */
        void onFailed(String desc);
    }
}
