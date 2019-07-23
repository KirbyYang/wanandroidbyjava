package com.wanandroid.java.modulebase.base.rx;

import com.wanandroid.java.modulebase.base.http.ApiException;
import com.wanandroid.java.modulebase.base.http.ExceptionHandler;
import com.wanandroid.java.modulebase.base.simple.core.mvp.BaseView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by YZH on 2019/7/18.
 * From the BaoBao project
 *
 * @author YZH
 */
public abstract class BaseObserver<T> extends DisposableObserver<BaseResponse<T>> {
    private BaseView baseView;

    public BaseObserver() {

    }

    public BaseObserver(BaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (baseView != null) {
            baseView.showLoadingDialog();
        }
    }

    @Override
    public void onNext(BaseResponse<T> baseResponse) {
        if (baseView != null) {
            baseView.dismissLoadingDialog();
        }

        int errcode = baseResponse.getErrorCode();
        String errmsg = baseResponse.getErrorMsg();
        // wanandroid api
        if (errcode == 0 || errcode == 200) {
            T data = baseResponse.getData();
            // 将服务端获取到的正常数据传递给上层调用方
            onSuccess(data);
        }else {
            onError(new ApiException(errcode, errmsg));
        }
    }

    /**
     * 回调正常数据
     *
     * @param data 结果
     */
    protected abstract void onSuccess(T data);

    /**
     * 异常处理，包括两方面数据：
     * (1) 服务端没有没有返回数据，HttpException，如网络异常，连接超时;
     * (2) 服务端返回了数据，但 errcode!=0,即服务端返回的data为空，如 密码错误,App登陆超时,token失效
     */
    @Override
    public void onError(Throwable e) {
        ExceptionHandler.handleException(e);
    }

    @Override
    public void onComplete() {
        if (baseView != null) {
            baseView.dismissLoadingDialog();
        }
    }
}
