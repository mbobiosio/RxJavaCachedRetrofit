package com.mbobiosio.rxjavacachedretrofit.utils;

import com.mbobiosio.rxjavacachedretrofit.data.api.ApiService;
import com.mbobiosio.rxjavacachedretrofit.data.api.RetrofitManager;
import com.mbobiosio.rxjavacachedretrofit.model.DailyModel;

import io.reactivex.Single;

/**
 * Created by Mbuodile Obiosio on Aug 14,2019
 * https://twitter.com/cazewonder
 * Nigeria.
 */
public class DevUtil {

    private ApiService mRetrofitManager, mRetrofitManagerCached;

    public DevUtil(RetrofitManager retrofitManager) {
        mRetrofitManager = retrofitManager.getDevRetrofit().create(ApiService.class);
        mRetrofitManagerCached = retrofitManager.getCachedDevRetrofit().create(ApiService.class);
    }

    public Single<DailyModel> getDevs() {
        return mRetrofitManager.getDevotionals();
    }

    public Single<DailyModel> getDevsCache() {
        return mRetrofitManagerCached.getDevotionals();
    }

}
