package com.longrise.android.bbt.lib_mvp.base.core.bindview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.longrise.android.bbt.lib_mvp.BaseBindActivity;
import com.longrise.android.bbt.lib_mvp.cache.Strings;
import com.longrise.android.bbt.lib_mvp.util.ObjectUtil;

/**
 * Created by godliness on 2018/08/31
 *
 * @author godliness
 *         管理绑定View
 *         <p>
 *         BaseBindView生命周期根据所依附的Activity发生变化，因此检测其发生内存
 *         泄漏入口变得相对容易。
 */
public abstract class BaseBindView<T> {

    private static final String TAG = "BaseBindView";

    private T mTarget;

    protected BaseBindView() {

    }

    /**
     * @param target ? extends BaseBindActivity
     * @see BaseBindActivity#initView#attachTarget(Object)
     */
    public final void attachTarget(T target) {
        mTarget = target;
        bindView();
        regEvent(true);
    }

    /**
     * @see BaseBindActivity#onDestroy()#detachTarget()
     */
    public final void detachTarget() {
        mTarget = null;
        regEvent(false);
        onDestroy();
    }

    /**
     * @see Activity#findViewById(int)
     */
    @SuppressWarnings("unchecked")
    public final <V> V findViewById(int resId) {
        if (mTarget != null) {
            return (V) ((Activity) mTarget).findViewById(resId);
        }
        return null;
    }

    /**
     * @see T 该方法回到{@link T}实现中
     */
    public final void onClick(View v) {
        if (mTarget instanceof View.OnClickListener) {
            ((View.OnClickListener) mTarget).onClick(v);
        } else {
            if (mTarget != null) {
                Toast.makeText(v.getContext(), Strings.getStrings().append("please in < ").append(ObjectUtil.getSimpleName(mTarget)).append(".java > implements View.OnClickListener").toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 绑定View
     */
    protected abstract void bindView();

    /**
     * 推荐在该方法中绑定相关事件，自动维护
     *
     * @param event true 绑定，false 解绑
     */
    protected abstract void regEvent(boolean event);

    /**
     * @see Activity#onResume()
     */
    public void onResume() {
    }

    /**
     * @see Activity#onPause()
     */
    public void onPause() {
    }

    /**
     * @see Activity#onSaveInstanceState(Bundle) ()
     */
    public void onSaveInstanceState(Bundle outState) {
    }

    /**
     * @see Activity#onRestoreInstanceState(Bundle) ()
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    /**
     * @see Activity#onDestroy()
     */
    protected abstract void onDestroy();

}
