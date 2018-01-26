package com.example.petsday.lab11.factory;

import com.example.petsday.lab11.service.GithubService;
import com.example.petsday.lab11.service.ReposService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by StellaSong on 2017/12/12.
 */

public class ServiceFactory {
    // 设置超时时间
    private static final int DEFAULT_TIME_OUT = 10;
    private static final int DEFAULT_READ_TIME_OUT = 30;

    public static final String BASE_URL = "https://api.github.com/";

    public ServiceFactory() {}

    public static GithubService createGithubService(){
        return createRetrofit(BASE_URL).create(GithubService.class);
    }

    public static ReposService createReposService() {
        return createRetrofit(BASE_URL).create(ReposService.class);
    }

    // 构造Retrofit对象实现网络访问，使用GsonConverterFactory解析返回的数据
    private static Retrofit createRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(createOkHttp())
                .build();
    }

    // 配置相应的OKHttp对象
    private static OkHttpClient createOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }
}
