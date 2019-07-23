package com.wanandroid.java.modulebase.base.rx;

/**
 * Created by YZH on 2019/7/18.
 * From the BaoBao project
 *
 * @author YZH
 */
public class BaseResponse<T> {
    private int errorCode = -1;
    private String errorMsg;
    private T data;
    /**
     * 兼容 gank api
     */
    private T results;
    private boolean error = true;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
