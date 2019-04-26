package com.wanandroid.java.base.simple.core.loadstyle.base;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wanandroid.java.BuildConfig;
import com.wanandroid.java.base.cache.Strings;
import com.wanandroid.java.base.simple.core.BaseMVPActivity;
import com.wanandroid.java.base.simple.core.loadstyle.LoadingStyle2;
import com.wanandroid.java.base.util.MvpLog;
import com.wanandroid.java.base.util.ObjectUtil;

/**
 * Created by godliness on 2019/1/10.
 * From the BaoBao project
 *
 * @author godliness
 *         统一管理加载样式
 *         每个继承自BaseLoadStyle2的实例都会被{@link StorageLoadStyle2}管理
 *         {@link LoadingStyle2}
 */

public abstract class BaseLoadStyle2<T> implements View.OnClickListener {

    private static final String TAG = "BaseLoadStyle2";
    public static final int DEFAULT_RES = -1;

    private LayoutInflater mInflater;
    private IResetLoadListener mCallback;

    private String mUser;

    protected BaseLoadStyle2() {
        MvpLog.e(TAG, "new BaseLoadStyle2");
    }

    final void attachUser(T user) {
        if (mInflater == null) {
            initUserInflater(user);
        }
        if (user instanceof IResetLoadListener) {
            this.mCallback = (IResetLoadListener) user;
        } else {
            if (BuildConfig.DEBUG) {
                mUser = ObjectUtil.getSimpleName(user);
            }
        }
    }

    final void detachUser() {
        mCallback = null;
        removeTreeView();
    }

    /**
     * 开始加载
     *
     * @param contentView 需要添加加载样式的View
     */
    public abstract void loadingStart(ViewGroup contentView);

    /**
     * 加载出错
     *
     * @param contentView 需要添加加载样式的View
     * @param resId       图片资源ID{@link BaseLoadStyle2#DEFAULT_RES}
     * @param textId      文字资源ID{@link BaseLoadStyle2#DEFAULT_RES}
     */
    public abstract void loadingError(ViewGroup contentView, int resId, int textId);

    /**
     * 加载为空
     *
     * @param contentView 需要添加加载样式的View
     * @param resId       图片资源ID{@link BaseLoadStyle2#DEFAULT_RES}
     * @param textId      文字资源ID{@link BaseLoadStyle2#DEFAULT_RES}
     */
    public abstract void loadingEmpty(ViewGroup contentView, int resId, int textId);

    /**
     * 无网络连接
     *
     * @param contentView 需要添加加载样式的View
     * @param resId       图片资源ID{@link BaseLoadStyle2#DEFAULT_RES}
     * @param textId      文字资源ID{@link BaseLoadStyle2#DEFAULT_RES}
     */
    public abstract void loadingNetWork(ViewGroup contentView, int resId, int textId);

    /**
     * 加载完成
     */
    public abstract void loadingNormal();

    protected abstract void removeTreeView();

    protected abstract void release();

    @Override
    public final void onClick(View view) {
        if (mCallback != null) {
            mCallback.onLoadReset();
        } else {
            if (!TextUtils.isEmpty(mUser)) {
                Toast.makeText(view.getContext(), Strings.getStrings().append("please in < ").append(mUser).append(".java > implements IResetLoadListener").toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected final void removeSelf(View viewSelf) {
        if (viewSelf != null) {
            ViewParent vp = viewSelf.getParent();
            if (vp != null) {
                ((ViewGroup) vp).removeView(viewSelf);
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected final <V> V findViewById(View view, int resId) {
        return (V) view.findViewById(resId);
    }

    protected final void bindImageResource(View view, int ivId, int resId) {
        if (resId != -1) {
            ImageView iv = findViewById(view, ivId);
            iv.setImageResource(resId);
        }
    }

    protected final void bindTextResource(View view, int tvId, int resId) {
        if (resId != -1) {
            TextView tv = findViewById(view, tvId);
            tv.setText(resId);
        }
    }

    protected final void bindLoadStyle(ViewGroup parent, View loadStyle) {
        if (parent != null && loadStyle != null) {
            ViewParent vp = loadStyle.getParent();
            if (vp != null) {
                ((ViewGroup) vp).removeView(loadStyle);
            }
            parent.addView(loadStyle);
        }
    }

    protected final View inflaterLoadStyle(ViewGroup parent, int layoutId) {
        return mInflater.inflate(layoutId, parent, false);
    }

    private void initUserInflater(T user) {
        if (user instanceof BaseMVPActivity) {
            mInflater = LayoutInflater.from(((BaseMVPActivity) user).getApplicationContext());
        } else {
            throw new IllegalArgumentException(Strings.getStrings().append("The < ").append(ObjectUtil.getSimpleName(user)).append(" > not supported type").toString());
        }
    }
}
