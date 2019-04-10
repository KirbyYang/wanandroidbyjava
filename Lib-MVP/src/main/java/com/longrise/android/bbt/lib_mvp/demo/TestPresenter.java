package com.longrise.android.bbt.lib_mvp.demo;

import android.os.Handler;
import android.os.Message;

import com.longrise.android.bbt.lib_mvp.base.BaseMVPActivity;
import com.longrise.android.bbt.lib_mvp.base.core.mvp.BasePresenter;

/**
 * Created by godliness on 2019/1/12.
 * From the BaoBao project
 *
 * @author godliness
 */

public class TestPresenter extends BasePresenter<TestView> {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    getV().showLoadingDialog();
                    sendEmptyMessageDelayed(1, 3000);
                    break;

                case 1:
                    getV().showLoadingError();
                    sendEmptyMessageDelayed(2, 3000);
                    break;

                case 2:
                    getV().showLoadingEmpty();
                    sendEmptyMessageDelayed(3, 3000);
                    break;

                case 3:
                    getV().dismissLoadingDialog();
                    sendEmptyMessageDelayed(0, 3000);
                    break;

                default:
                    break;
            }
        }
    };


    /**
     * 该方法在{@link BaseMVPActivity#initView()#init()}之后回调
     */
    @Override
    public void init() {


    }

    /**
     * 在Presenter申请的资源应该在这里释放
     */
    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);

    }

    public void requestData() {
        mHandler.sendEmptyMessage(0);
    }

}
