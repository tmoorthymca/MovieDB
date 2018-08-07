package com.mithran.tm_base_library;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;

/**
 * Created by Mithran on 07/08/2018.
 */

public class Service {

    private IExampleNetwork mIExampleNetwork, mICachedExampleNetwork;

    public Service(RetrofitManager retrofitManager) {
        mIExampleNetwork = retrofitManager.getRetrofit(null).create(IExampleNetwork.class);
        mICachedExampleNetwork = retrofitManager.getCachedRetrofit(null).create(IExampleNetwork.class);
    }

    interface IExampleNetwork {
        @GET("movie/upcoming")
        Single<String> getUpcomingMovie(@Query("api_key")String apiKey, @Query("language")String language, @Query("page")int page);
    }/*

    public Single<String> getDetails(String id) {
        return mIExampleNetwork.getDetails(id);
    }*/

    public Single<String> getCachedDetails() {
        Map<String,String> map = new HashMap();
        map.put("Test","Test");
        return mICachedExampleNetwork.getUpcomingMovie(RetrofitManager.API_KEY,"En",1);
    }
}
