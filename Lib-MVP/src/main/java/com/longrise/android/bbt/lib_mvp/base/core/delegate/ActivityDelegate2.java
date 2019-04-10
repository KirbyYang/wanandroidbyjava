package com.longrise.android.bbt.lib_mvp.base.core.delegate;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longrise.android.bbt.lib_mvp.BaseActivity;
import com.longrise.android.bbt.lib_mvp.R;
import com.longrise.android.bbt.lib_mvp.base.core.delegate.base.BaseDelegate2;
import com.longrise.android.bbt.lib_mvp.base.core.delegate.base.IActivityDelegateListener;
import com.longrise.android.bbt.lib_mvp.util.MvpLog;
import com.longrise.android.bbt.lib_mvp.util.ObjectUtil;
import com.longrise.android.bbt.lib_mvp.util.ViewRelease;


/**
 * Created by godliness on 2019/1/12.
 * From the BaoBao project
 *
 * @author godliness
 *         BaseActivity辅助管理模板样式
 */

public final class ActivityDelegate2 extends BaseDelegate2<BaseActivity> implements View.OnClickListener {

    private static final String TAG = "ActivityDelegate2";

    public static final int ID_DELEGATE_CONTENT = R.id.content_activity_delegate;

    private LinearLayout mDelegateLayout;
    private View mTitleView;
    private Toolbar mToolbar;
    private FrameLayout mContentView;

    private ViewStub mStubRightText;
    private View mRightText;
    private ViewStub mStubRightIcon;
    private View mRightIcon;

    /**
     * @see BaseActivity
     */
    private IActivityDelegateListener mCallback;

    @SuppressWarnings("unchecked")
    public static <Delegate extends BaseDelegate2, T> Delegate findDelegate(T target) {
        return (Delegate) findDelegate(ActivityDelegate2.class, target);
    }

    public ViewGroup getDelegateLayout() {
        return mDelegateLayout;
    }

    /**
     * 请求ActivityDelegate样式
     */
    public void requestDelegateFeature(View customTitleView, int titleId, int resId, int textId) {
        if (resId != 0 && textId != 0) {
            throw new IllegalStateException("overrideRightIcon() and overrideRightText() only use one");
        }
        if (customTitleView != null && (titleId != 0 || resId != 0 || textId != 0)) {
            throw new IllegalStateException("when customTitleView(), hasTitle() || overrideRightIcon() || overrideRightText() must be return default value");
        }
        //install delegate layout style
        installDelegateFeature(customTitleView);
        if (customTitleView == null) {
            installTitleFeature(titleId, resId, textId);
        }
    }

    /**
     * 绑定View
     */
    public void bindView(BaseActivity target, int layoutId) {
        LayoutInflater.from(target).inflate(layoutId, mContentView);
        ViewGroup.LayoutParams delegateLayoutParams = mDelegateLayout.getLayoutParams();
        if (delegateLayoutParams == null) {
            delegateLayoutParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        target.setContentView(mDelegateLayout, delegateLayoutParams);
    }

    @Override
    protected void init(BaseActivity target) {
        if (mDelegateLayout == null) {
            mDelegateLayout = inflaterDelegate(R.layout.lib_mvp_delegate_layout);
            mTitleView = findViewById(R.id.abl_activity_delegate_lib_mvp);
            mToolbar = findViewById(R.id.toolbar_activity_delegate_lib_mvp);
            mToolbar.setTitle("");
            mToolbar.setNavigationOnClickListener(this);
            mContentView = findViewById(ID_DELEGATE_CONTENT);

            mStubRightIcon = findViewById(R.id.vs_right_icon_titlebar_lib_mvp);
            mStubRightText = findViewById(R.id.vs_right_text_titlebar_lib_mvp);
        }
        if (target != null) {
            this.mCallback = (IActivityDelegateListener) target;
        }

        MvpLog.e(TAG, "init");
    }

    @Override
    protected void removeTreeView() {
        removeSelf(mDelegateLayout);
        View titleView = mDelegateLayout.getChildAt(0);
        if (!ObjectUtil.equals(titleView, mTitleView)) {
            //移除自定义标题栏View
            removeSelf(titleView);
        }
        ViewRelease.releaseDrawCache(mContentView);
        //移除所有childView
        if (mContentView != null) {
            mContentView.removeAllViews();
        }
        MvpLog.e(TAG, "removeTreeView");
    }

    @Override
    protected void recoverState() {
        if (mToolbar != null) {
            mToolbar.setTitle("");
        }
        mCallback = null;

        MvpLog.e(TAG, "recoverState");
    }

    @Override
    protected void release() {
        mDelegateLayout = null;
        mTitleView = null;
        mToolbar = null;
        mContentView = null;
        mStubRightText = null;
        mRightText = null;
        mStubRightIcon = null;
        mRightIcon = null;
        MvpLog.e(TAG, "release");
    }

    @SuppressWarnings("unchecked")
    protected final <T> T findViewById(int id) {
        return (T) mDelegateLayout.findViewById(id);
    }

    @Override
    public void onClick(View v) {
        if (mCallback != null) {
            int id = v.getId();
            if (id == R.id.ll_right_text_titlebar_lib_mvp) {
                mCallback.delegateRightText();
            } else if (id == R.id.ll_right_icon_titlebar_lib_mvp) {
                mCallback.delegateRightIcon();
            } else {
                mCallback.delegateBackPressed();
            }
        }
    }

    private void bindRightIcon(int resId) {
        if (mRightIcon == null) {
            mRightIcon = mStubRightIcon.inflate();
            mRightIcon.setOnClickListener(this);
        } else {
            mRightIcon.setVisibility(View.VISIBLE);
        }
        ImageView rightIv = findViewById(R.id.iv_right_titlebar_lib_mvp);
        if (rightIv != null) {
            rightIv.setImageResource(resId);
        }
    }

    private void bindRightText(int text) {
        if (mRightText == null) {
            mRightText = mStubRightText.inflate();
            mRightText.setOnClickListener(this);
        } else {
            mRightText.setVisibility(View.VISIBLE);
        }
        TextView rightTv = findViewById(R.id.tv_right_titlebar_lib_mvp);
        if (rightTv != null) {
            rightTv.setText(text);
        }
    }

    private void installTitleFeature(int titleId, int resId, int textId) {
        bindTitle(titleId);
        if (resId != 0) {
            bindRightIcon(resId);
            return;
        }
        if (mRightIcon != null) {
            mRightIcon.setVisibility(View.GONE);
        }
        if (textId != 0) {
            bindRightText(textId);
            return;
        }
        if (mRightText != null) {
            mRightText.setVisibility(View.GONE);
        }
    }

    private void installDelegateFeature(View customTitleView) {
        if (customTitleView == null && mTitleView.getParent() != null) {
            return;
        }
        if (mDelegateLayout != null) {
            mDelegateLayout.removeAllViews();
        }
        if (customTitleView != null) {
            addDelegateLayout(customTitleView);
        } else {
            addDelegateLayout(mTitleView);
        }
    }

    private void addDelegateLayout(View titleView) {
        if (mDelegateLayout != null) {
            mDelegateLayout.addView(titleView);
            mDelegateLayout.addView(mContentView);
        }
    }

    private void bindTitle(int title) {
        if (mToolbar != null) {
            mToolbar.setTitle(title);
        }
    }
}
