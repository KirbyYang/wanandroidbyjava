package com.wanandroid.java.modulemain.apiservice;

import com.wanandroid.java.modulebase.base.rx.BaseResponse;
import com.wanandroid.java.modulemain.bean.BannerResult;
import com.wanandroid.java.modulemain.bean.HomeArticleResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by YZH on 2019/7/18.
 * From the BaoBao project
 *
 * @author YZH
 */
public interface ApiService {
    /**
     * 获取首页 banner 数据
     *
     * @return
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerResult>>> getBanner();

    /**
     * 获取首页文章列表
     *
     * @param page
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<BaseResponse<HomeArticleResult>> getHomeArticles(@Path("page") int page);
}
