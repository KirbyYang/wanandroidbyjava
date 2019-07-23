package com.wanandroid.java.modulebase.base.http;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.wanandroid.java.modulebase.base.init.AppContext;
import com.wanandroid.java.modulebase.base.json.FastJsonConverterFactory;

import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by YZH on 2019/7/17.
 * From the BaoBao project
 *
 * @author YZH
 */
public class RetrofitClient {
    private static final String API_HOST = "https://www.wanandroid.com/";
    private static RetrofitClient instance;
    private final OkHttpClient okHttpClient;
    private final Retrofit retrofit;

    private RetrofitClient() {
        PersistentCookieJar persistentCookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(AppContext.get()));

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .cookieJar(persistentCookieJar)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .addInterceptor(InterceptorUtil.headerInterceptor())
                .addInterceptor(InterceptorUtil.logInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .client(okHttpClient)
                .build();

    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
