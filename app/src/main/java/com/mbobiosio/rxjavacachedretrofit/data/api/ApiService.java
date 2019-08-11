package com.mbobiosio.rxjavacachedretrofit.data.api;

import com.mbobiosio.rxjavacachedretrofit.model.WeekResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by Mbuodile Obiosio on Aug 11,2019
 * https://twitter.com/cazewonder
 * Nigeria.
 */
public interface ApiService {

    @Headers("Content-Type:application/json")
    @GET("read.php")
    Single<WeekResponse> getLessons();
}
