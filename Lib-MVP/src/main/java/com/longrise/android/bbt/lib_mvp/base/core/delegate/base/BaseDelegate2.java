package com.longrise.android.bbt.lib_mvp.base.core.delegate.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.longrise.android.bbt.lib_mvp.BaseActivity;
import com.longrise.android.bbt.lib_mvp.cache.Strings;
import com.longrise.android.bbt.lib_mvp.util.ObjectUtil;

/**
 * Created by godliness on 2019/1/13.
 * From the BaoBao project
 *
 * @author godliness
 *         Delegate基类
 *         每个继承自BaseDelegate的实例都会自动被{@link StorageDelegate2}管理
 */

public abstract class BaseDelegate2<T> {

    private LayoutInflater mInflater;
    private boolean mRecycle;

    protected BaseDelegate2() {

    }

    /**
     * 获取{@link BaseDelegate2}实例
     *
     * @param delegate {@link BaseDelegate2}子类
     * @param target   T
     */
    protected static <Delegate extends BaseDelegate2, T> Delegate findDelegate(Class<Delegate> delegate, T target) {
        return StorageDelegate2.obtion(delegate, target);
    }

    /**
     * 回收当前实例
     */
    public final void recycleDelegate() {
        StorageDelegate2.recycle(this);
    }

    final void attachDelegate(T target) {
        if (mInflater == null) {
            initDelegateInflater(target);
        }
        init(target);
        mRecycle = false;
    }

    final void detachDelegate() {
        removeTreeView();
        recoverState();
        mRecycle = true;
    }

    /**
     * @param target 当前代理目标
     */
    protected abstract void init(T target);

    protected abstract void removeTreeView();

    protected abstract void recoverState();

    protected abstract void release();

    protected final boolean isRecycle() {
        return mRecycle;
    }

    protected final void removeSelf(View self) {
        if (self != null) {
            ViewParent vp = self.getParent();
            if (vp != null) {
                ((ViewGroup) vp).removeView(self);
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected final <V extends View> V inflaterDelegate(int layoutId) {
        return (V) mInflater.inflate(layoutId, null, false);
    }

    private void initDelegateInflater(T target) {
        if (target instanceof BaseActivity) {
            mInflater = LayoutInflater.from(((BaseActivity) target).getApplicationContext());
        } else {
            throw new IllegalArgumentException(Strings.getStrings().append("The < ").append(ObjectUtil.getSimpleName(target)).append(" > not supported type").toString());
        }
    }
}
