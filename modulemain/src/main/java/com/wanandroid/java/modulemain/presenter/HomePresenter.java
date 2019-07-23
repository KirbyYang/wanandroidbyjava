package com.wanandroid.java.modulemain.presenter;

import com.wanandroid.java.modulebase.base.rx.BaseObserver;
import com.wanandroid.java.modulebase.base.simple.core.mvp.BasePresenter;
import com.wanandroid.java.modulemain.apiservice.ApiService;
import com.wanandroid.java.modulemain.bean.BannerResult;
import com.wanandroid.java.modulemain.bean.HomeArticleResult;
import com.wanandroid.java.modulemain.contract.HomeContract;

import java.util.List;

/**
 * Created by YZH on 2019/7/18.
 * From the BaoBao project
 *
 * @author YZH
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter{
    @Override
    public void init() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void getBanner() {
        addSubscribe(create(ApiService.class).getBanner(), new BaseObserver<List<BannerResult>>(getV()) {
            @Override
            protected void onSuccess(List<BannerResult> data) {
                if (!isFinishing()) {
                    getV().onBanner(data);
                }
            }
        });
    }

    /**
     * 获取首页文章数据
     *
     * @param page
     */
    @Override
    public void getHomeArticles(int page) {
        addSubscribe(create(ApiService.class).getHomeArticles(page), new BaseObserver<HomeArticleResult>(getV()) {
            @Override
            protected void onSuccess(HomeArticleResult data) {
                if (!isFinishing()) {
                    getV().onHomeArticles(data);
                }
            }
        });
    }
}
