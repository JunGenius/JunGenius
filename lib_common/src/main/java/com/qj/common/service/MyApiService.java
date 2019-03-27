package com.qj.common.service;


import com.qj.common.model.WanAndroidInfo;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyApiService {
    /**
     *普通写法
     */
    @GET("article/list/{page}/json")
    Observable<WanAndroidInfo> getData(@Path("page") int page);

    @GET("article/list/0/json?cid=60")
    Observable<Response> getData1();
}
