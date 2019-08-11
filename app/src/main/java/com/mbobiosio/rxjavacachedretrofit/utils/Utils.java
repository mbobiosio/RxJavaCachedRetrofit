package com.mbobiosio.rxjavacachedretrofit.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.signature.ObjectKey;
import com.mbobiosio.rxjavacachedretrofit.data.api.ApiService;
import com.mbobiosio.rxjavacachedretrofit.data.api.RetrofitManager;
import com.mbobiosio.rxjavacachedretrofit.model.WeekResponse;

import io.reactivex.Single;

/**
 * Created by Mbuodile Obiosio on Aug 11,2019
 * https://twitter.com/cazewonder
 * Nigeria.
 */
public class Utils {

    private ApiService mRetrofitManager, mRetrofitManagerCached;

    public Utils(RetrofitManager retrofitManager) {
        mRetrofitManager = retrofitManager.getRetrofit().create(ApiService.class);
        mRetrofitManagerCached = retrofitManager.getCachedRetrofit().create(ApiService.class);
    }

    public Single<WeekResponse> getLessons() {
        return mRetrofitManager.getLessons();
    }

    public Single<WeekResponse> getCachedLessons() {
        return mRetrofitManagerCached.getLessons();
    }

    public static boolean checkStringNotEmpty(String str) {
        return (str != null && !str.equals(""));
    }

    public static void loadGlideImage(Context context, ImageView imageView, String url) {
        if (Utils.checkStringNotEmpty(url)) {
            GlideApp.with(context.getApplicationContext())
                    .load(url)
                    .signature(new ObjectKey(url))
                    .dontAnimate()
                    .into(imageView);
        }
    }
}
