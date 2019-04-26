package com.wanandroid.java.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.wanandroid.java.BuildConfig;
import com.wanandroid.java.base.cache.Strings;
import com.wanandroid.java.base.simple.core.BaseMVPActivity;
import com.wanandroid.java.base.simple.core.delegate.ActivityDelegate2;
import com.wanandroid.java.base.simple.core.delegate.base.IActivityDelegateListener;
import com.wanandroid.java.base.simple.core.loadstyle.LoadingStyle2;
import com.wanandroid.java.base.simple.core.mvp.BasePresenter;
import com.wanandroid.java.base.util.NetUtil;
import com.wanandroid.java.base.util.ObjectUtil;

/**
 * Created by godliness on 2018/12/30.
 * From the BaoBao project
 *
 * @author godliness
 *         1、提供统一模板样式的Activity界面：主要包含标题栏、内容区域。
 *         2、{@link BaseBindActivity}为进一步简化Activity
 *         3、如果存在特殊界面样式需求{@link BaseMVPActivity}
 *         <p>
 *         <p>
 *         1、尽可能降低Activity代码耦合，明确各部分职能分工。
 *         2、对Presenter、BindView增加生命周期管理，便于切入发现更细粒度的内存泄漏。
 *         3、通过兜底策略，发生泄漏的Activity成为不占资源的空壳。
 *         4、严格约束相关API，如Bundle获取，广播注册等。
 */

public abstract class BaseActivity<P extends BasePresenter> extends BaseMVPActivity<P> implements IActivityDelegateListener {

    /**
     * 辅助完成统一样板
     */
    private ActivityDelegate2 mDelegate2;

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        initDelegate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public final void setContentView(@LayoutRes int layoutResID) {
        if (mDelegate2 != null) {
            mDelegate2.bindView(this, layoutResID);
        }
    }

    /**
     * 自定义标题栏，返回自定义标题栏布局
     */
    protected int customTitleLayout() {
        return 0;
    }

    /**
     * 默认标题栏右侧图片按钮，与{@link #overrideRightText()}互斥
     *
     * @return resId
     */
    protected int overrideRightIcon() {
        return 0;
    }

    /**
     * 默认标题栏右侧文字按钮，与{@link #overrideRightIcon()}互斥
     *
     * @return String
     */
    protected int overrideRightText() {
        return 0;
    }

    /**
     * @see ActivityDelegate2
     */
    @Override
    public void delegateBackPressed() {
        //1、直接finish当前Activity，如果含有Fragment回退栈，请自行实现。
        //2、我们非常不推荐使用Fragment回退栈。
        finish();
    }

    @Override
    public void delegateRightIcon() {
        if (BuildConfig.DEBUG) {
            Toast.makeText(this, Strings.getStrings().append("please in ").append(getClass().getSimpleName()).append(" override < delegateRightIcon() > ").toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void delegateRightText() {
        if (BuildConfig.DEBUG) {
            Toast.makeText(this, Strings.getStrings().append("please in ").append(getClass().getSimpleName()).append(" override < delegateRightText() >").toString(), Toast.LENGTH_SHORT).show();
        }
    }

    protected final ActivityDelegate2 getDelegate2() {
        return mDelegate2;
    }

    @Override
    public void showLoadingDialog() {
        if (hasLoadStyle()) {
            LoadingStyle2.loadingStart(this);
        } else {
            if (BuildConfig.DEBUG) {
                illegalStateLoadStyle();
            }
        }
    }

    @Override
    public void showLoadingError() {
        if (hasLoadStyle()) {
            if (NetUtil.isNetWorkEnable(this)) {
                LoadingStyle2.loadingError(this);
            } else {
                LoadingStyle2.loadingNoneNetWork(this);
            }
        } else {
            if (BuildConfig.DEBUG) {
                illegalStateLoadStyle();
            }
        }
    }

    @Override
    public void showLoadingEmpty() {
        if (hasLoadStyle()) {
            LoadingStyle2.loadingEmpty(this);
        } else {
            if (BuildConfig.DEBUG) {
                illegalStateLoadStyle();
            }
        }
    }

    @Override
    public void dismissLoadingDialog() {
        if (hasLoadStyle()) {
            LoadingStyle2.loadingNormal(this);
        } else {
            if (BuildConfig.DEBUG) {
                illegalStateLoadStyle();
            }
        }
    }

    /**
     * 返回当前页面标题，如果是{@link #customTitleLayout()}返回默认值即可
     */
    protected abstract int hasTitle();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Delegate的释放要保证在LoadStyle释放之前
        if (mDelegate2 != null) {
            mDelegate2.recycleDelegate();
        }
    }

    private void initDelegate(Bundle state) {
        if (mDelegate2 == null) {
            mDelegate2 = ActivityDelegate2.findDelegate(this);
        }
        if (mDelegate2 != null) {
            View customTitleView = null;
            int customTitleLayout = customTitleLayout();
            if (customTitleLayout != 0) {
                customTitleView = LayoutInflater.from(this).inflate(customTitleLayout, mDelegate2.getDelegateLayout(), false);
            }
            //搭建Delegate样式
            mDelegate2.requestDelegateFeature(customTitleView, hasTitle(), overrideRightIcon(), overrideRightText());
        }
    }

    private void illegalStateLoadStyle() {
        if (!hasLoadStyle()) {
            throw new IllegalStateException(Strings.getStrings().append("please in ").append(ObjectUtil.getSimpleName(this)).append(" hasLoadStyle() and return true").toString());
        }
    }
}
