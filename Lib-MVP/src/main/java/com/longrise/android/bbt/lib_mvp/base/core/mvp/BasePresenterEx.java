package com.longrise.android.bbt.lib_mvp.base.core.mvp;

import com.longrise.android.bbt.lib_mvp.util.GenericUtil;

/**
 * Created by godliness on 2018/12/30.
 * From the BaoBao project
 *
 * @author godliness
 *         MVP结构中的Presenter，对BasePresenter进行扩展
 *         将数据获取分离到Model中
 */

public abstract class BasePresenterEx<M, V> extends BasePresenter<V> {

    /**
     * @see BaseModel
     */
    protected final M mModel;

    protected BasePresenterEx() {
        mModel = GenericUtil.getT(this, 0);
        if (mModel != null) {
            ((BaseModel) mModel).init();
        }
    }
}
