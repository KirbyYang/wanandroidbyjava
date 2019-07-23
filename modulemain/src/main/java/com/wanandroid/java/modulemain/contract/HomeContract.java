package com.wanandroid.java.modulemain.contract;

import com.wanandroid.java.modulebase.base.simple.core.mvp.BaseView;
import com.wanandroid.java.modulemain.bean.BannerResult;
import com.wanandroid.java.modulemain.bean.HomeArticleResult;

import java.util.List;

/**
 * Created by YZH on 2019/7/18.
 * From the BaoBao project
 *
 * @author YZH
 */
public interface HomeContract {

    interface View extends BaseView {
        /**
         * banner 数据回调
         */
        void onBanner(List<BannerResult> bannerResults);


        /**
         * 首页文章列表数据回调
         *
         * @param result
         */
        void onHomeArticles(HomeArticleResult result);
    }

    interface Presenter {
        /**
         * 获取 banner 数据
         */
        void getBanner();

        /**
         * 获取首页文章列表
         */
        void getHomeArticles(int page);

    }
}
