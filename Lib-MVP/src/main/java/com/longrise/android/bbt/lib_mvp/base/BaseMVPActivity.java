package com.longrise.android.bbt.lib_mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.longrise.android.bbt.lib_mvp.BaseActivity;
import com.longrise.android.bbt.lib_mvp.base.core.loadstyle2.LoadingStyle2;
import com.longrise.android.bbt.lib_mvp.base.core.mvp.BasePresenter;
import com.longrise.android.bbt.lib_mvp.base.core.mvp.BaseView;
import com.longrise.android.bbt.lib_mvp.util.GenericUtil;
import com.longrise.android.bbt.lib_mvp.util.ViewRelease;

/**
 * Created by godliness on 2018/12/30.
 * From the BaoBao project
 *
 * @author godliness
 *         优秀的框架设计能够减少甚至避免程序员犯错，当然这并不容易。这也是促使不断进行改进、完善的源泉。
 *         类的代码结构越小，Bug越不容易出现，越容易调试，更容易测试，相信这一点大家都是赞同的。
 *         在MVP结构模式下，View和Model是完全分离没有任何关联(如在View层完全不用导入Model包，也不应该去关联他们)
 *         使用MVP结构模式能够更方便的帮助Activity或Fragment职责分离，减小类体积，是项目结构更加清晰
 *         <p>
 *         该怎样从架构级别去搭建一个App，怎样让他们对日益更改的界面与业务逻辑？这很不容易！
 *         ①内存泄漏：至少保证Activity与Fragment、Dialog不发生内存泄漏。
 *         ②代码耦合：降低代码耦不仅增强代码可读性(臃肿)，也是有利于规避内存泄露的有效手段
 *         <p>
 *         1、主要负责MVP框架结构的创建与销毁
 *         2、维护加载样式生命周期 {@link BaseView}
 *         <p>
 *         特别注意{@link LoadingStyle2} 对BaseMVPActivity没有默认实现
 */

public abstract class BaseMVPActivity<P extends BasePresenter> extends BaseSuperActivity {

    private static final String TAG = "BaseMVPActivity";

    /**
     * @see BasePresenter
     */
    public P mPresenter;
    private boolean mContentChanged;

    /**
     * 返回当前Activity的xml布局资源
     *
     * @param savedInstanceState Bundle
     */
    protected abstract int getContentViewId(Bundle savedInstanceState);

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 推荐在该方法完成相关事件绑定，在{@link #initView()}之后回调，自动维护
     *
     * @param event true注册，false反注册
     */
    protected abstract void regEvent(boolean event);

    /**
     * 是否需要默认加载样式
     */
    protected boolean hasLoadStyle() {
        return true;
    }

    /**
     * 开始加载
     *
     * @see BaseView#showLoadingDialog()
     */
    public void showLoadingDialog() {
        LoadingStyle2.findLoadStyle(this).loadingStart((ViewGroup) getWindow().getDecorView());
    }

    /**
     * 加载出错
     *
     * @see LoadingStyle2#findLoadStyle(Object)
     */
    public abstract void showLoadingError();

    /**
     * 加载为空
     *
     * @see LoadingStyle2#findLoadStyle(Object)
     */
    public abstract void showLoadingEmpty();

    /**
     * 加载完成
     *
     * @see BaseView#dismissLoadingDialog()
     */
    public void dismissLoadingDialog() {
        LoadingStyle2.findLoadStyle(this).loadingNormal();
    }

    /**
     * 辅助findViewById
     */
    @SuppressWarnings("unchecked")
    public final <T> T findViewById2(int resId) {
        return (T) super.findViewById(resId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getContentViewId(savedInstanceState);
        if (layoutId != 0) {
            setContentView(layoutId);
        }
    }

    @Override
    public void onContentChanged() {
        if (!mContentChanged) {
            initMVPFrame();
            initView();
            if (mPresenter != null && this instanceof BaseView) {
                mPresenter.init();
            }
            regEvent(true);
            //主要用于防止getWindow().addView()
            mContentChanged = true;
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachTarget();
        }
        if (hasLoadStyle()) {
            LoadingStyle2.onDestroy(this);
        }
        regEvent(false);
        leastRelease();
        super.onDestroy();
    }

    private void leastRelease() {
        if (!(this instanceof BaseActivity)) {
            ViewRelease.releaseDrawCache(getWindow());
        }
    }

    @SuppressWarnings("unchecked")
    private void initMVPFrame() {
        mPresenter = GenericUtil.getT(this, 0);
        if (mPresenter != null && this instanceof BaseView) {
            mPresenter.attachTarget(this);
            mPresenter.attachV(this);
        }
    }
}
