package com.wanandroid.java.modulemain.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wanandroid.java.modulebase.base.BaseFragment;
import com.wanandroid.java.modulebase.base.util.DisplayInfoUtils;
import com.wanandroid.java.modulebase.base.util.MultiStateUtils;
import com.wanandroid.java.modulebase.base.util.SmartRefreshUtils;
import com.wanandroid.java.modulemain.R;
import com.wanandroid.java.modulemain.R2;
import com.wanandroid.java.modulemain.adapter.HomeAdapter;
import com.wanandroid.java.modulemain.bean.BannerResult;
import com.wanandroid.java.modulemain.bean.HomeArticleResult;
import com.wanandroid.java.modulemain.contract.HomeContract;
import com.wanandroid.java.modulemain.imageloader.GlideImageLoader;
import com.wanandroid.java.modulemain.presenter.HomePresenter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

/**
 * Created by YZH on 2019/4/24.
 * From the BaoBao project
 *
 * @author YZH
 */
public class HomePageFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    public static final String TAG = HomePageFragment.class.getSimpleName();

    private static final int PAGE_START = 0;

    @BindView(R2.id.abc)
    ActionBarCommon abc;
    @BindView(R2.id.rv)
    RecyclerView rv;
    @BindView(R2.id.srl)
    SmartRefreshLayout srl;
    @BindView(R2.id.msv)
    MultiStateView msv;
    private Banner mBanner;

    private int currPage = PAGE_START;
    private SmartRefreshUtils mSmartRefreshUtils;
    private HomeAdapter mAdapter;

    private List<BannerResult> mBannerBeans;

    @Override
    protected int getLayoutResource(ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initView() {
        abc.setOnRightIconClickListener(new OnActionBarChildClickListener() {
            @Override
            public void onClick(View v) {
//                SearchActivity.start(getContext());
                Toast.makeText(getContext(),"搜索功能待开发",Toast.LENGTH_LONG).show();
            }
        });

        mSmartRefreshUtils = SmartRefreshUtils.with(srl);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                currPage = PAGE_START;
                mPresenter.getBanner();
//                if (SettingUtils.getInstance().isShowTop()) {
//                    presenter.getTopArticleList(true);
//                }
                mPresenter.getHomeArticles(currPage);
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HomeAdapter();
        mAdapter.addHeaderView(createHeaderBanner());
        mAdapter.setEnableLoadMore(false);
        rv.setAdapter(mAdapter);

//        mBanner = getView().findViewById(R.id.banner_home);
//        mBanner.setImageLoader(new GlideImageLoader());
        mPresenter.getBanner();

        // 请求首页文章列表
        mPresenter.getHomeArticles(currPage);
    }

    private View createHeaderBanner() {
        mBanner = new Banner(getContext());
        int height = (int) (DisplayInfoUtils.getInstance().getWidthPixels() * (9F / 16F));
        mBanner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        mBanner.setPadding(0,0,0,30);
//        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
//        mBanner.setBannerAnimation(Transformer.Default);
//        mBanner.startAutoPlay();
        mBanner.setImageLoader(new GlideImageLoader());
//        mBanner.setDelayTime(5000);
        mBanner.setIndicatorGravity(BannerConfig.LEFT);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                BannerBean bean = mBannerBeans.get(position);
//                WebActivity.start(getContext(), bean.getTitle(), bean.getUrl());
            }
        });
        return mBanner;
    }

    @Override
    protected void performLazyLoad() {

    }

    @Override
    protected void regEvent(boolean event) {

    }

    @Override
    public void onBanner(List<BannerResult> data) {
        mBannerBeans = data;
        List<String> urls = new ArrayList<>(data.size());
        List<String> titles = new ArrayList<>(data.size());
        for (BannerResult bean : data) {
            urls.add(bean.getImagePath());
            titles.add(bean.getTitle());
        }
        mBanner.setImages(urls);
        mBanner.setBannerTitles(titles);
        mBanner.start();
        MultiStateUtils.toContent(msv);
    }

    @Override
    public void onHomeArticles(HomeArticleResult result) {
        currPage = result.getCurPage();
        if (currPage == 1) {
            mAdapter.setNewData(result.getDatas());
            mAdapter.setEnableLoadMore(true);
        } else {
            mAdapter.addData(result.getDatas());
            mAdapter.loadMoreComplete();
        }
        if (result.isOver()) {
            mAdapter.loadMoreEnd();
        }
        mSmartRefreshUtils.success();
        MultiStateUtils.toContent(msv);
    }

    private List<String> getImages(List<BannerResult> bannerResults) {
        List<String> list = new ArrayList<>();
        if (bannerResults != null) {
            for (BannerResult bannerResult : bannerResults) {
                list.add(bannerResult.getImagePath());
            }
        }
        return list;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }
}
