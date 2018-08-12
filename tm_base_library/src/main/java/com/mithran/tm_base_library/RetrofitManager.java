package com.mithran.tm_base_library;

import android.content.Context;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

public class RetrofitManager<T> {
    public static final String TAG = "RetrofitManager";

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static String API_KEY = "7085aa44d49e3fca2114530e37f88055";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";

    private T service = null;
    private T serviceCache = null;
    private T serviceOrCache = null;

    private Context mContext;

    private Retrofit.Builder mRetrofit;

    private Cache mCache;
    private OkHttpClient mOkHttpClient,mServerWithCachedOkHttpClient,mCachedOkHttpClient;

    public static int FROM_SERVER = 1;
    public static int FROM_CACHE = 2;
    public static int FROM_SERVER_FIRST_CACHE_NEXT = 3;

    private static RetrofitManager retrofitManager;

    public static RetrofitManager getInstant(Context context){
        if(retrofitManager == null){
            retrofitManager = new RetrofitManager(context);
        }
        return retrofitManager;
    }

    public RetrofitManager(Context context) {
        mContext = context;
    }

    public Retrofit apiCall(String baseUrl,int type){
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        }

        if(type == FROM_SERVER){
            mRetrofit.client(okHttpClient());
        }else if(type == FROM_CACHE){
            mRetrofit.client(cachedOkHttpClient());
        }else if(type == FROM_SERVER_FIRST_CACHE_NEXT){
            mRetrofit.client(serverFirstCachedNextOkHttpClient());
        }

        if (!TextUtils.isEmpty(baseUrl)) {
            mRetrofit.baseUrl(baseUrl);
        } else {
            mRetrofit.baseUrl(BASE_URL);
        }
        return mRetrofit.build();
    }

    private OkHttpClient okHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .addInterceptor(provideHttpLoggingInterceptor());
            mOkHttpClient = httpClient.build();
        }
        return mOkHttpClient;
    }

    private OkHttpClient serverFirstCachedNextOkHttpClient() {
        if (mServerWithCachedOkHttpClient == null) {
            // Add all interceptors you want (headers, URL, logging)
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .addInterceptor(provideOfflineCacheInterceptor())
                    .addNetworkInterceptor(provideCacheInterceptor())
                    .addInterceptor(provideHttpLoggingInterceptor())
                    .cache(provideCache());

            mServerWithCachedOkHttpClient = httpClient.build();
        }
        return mServerWithCachedOkHttpClient;
    }

    private OkHttpClient cachedOkHttpClient() {
        if (mCachedOkHttpClient == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    // Add all interceptors you want (headers, URL, logging)
                    .addInterceptor(provideHttpLoggingInterceptor())
                    .addInterceptor(provideForcedOfflineCacheInterceptor())
                    .cache(provideCache());

            mCachedOkHttpClient = httpClient.build();
        }
        return mCachedOkHttpClient;
    }

    private Cache provideCache() {
        if (mCache == null) {
            try {
                mCache = new Cache(new File(mContext.getCacheDir(), "http-cache"),
                        10 * 1024 * 1024); // 10 MB
            } catch (Exception e) {
                Log.e(TAG, "Could not create Cache!");
            }
        }
        return mCache;
    }

    private Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                CacheControl cacheControl;

                if (RetrofitManager.this.isConnected()) {
                    cacheControl = new CacheControl.Builder()
                            .maxAge(0, TimeUnit.SECONDS)
                            .build();
                } else {
                    cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();
                }

                return response.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                        .build();

            }
        };
    }

    private Interceptor provideOfflineCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (!RetrofitManager.this.isConnected()) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .removeHeader(HEADER_PRAGMA)
                            .removeHeader(HEADER_CACHE_CONTROL)
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }

    //private Interceptor ProvideServerFirst

    private Interceptor provideForcedOfflineCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();

                request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build();

                return chain.proceed(request);
            }
        };
    }

    public void clean() {
        if (mOkHttpClient != null) {
            // Cancel Pending Request
            mOkHttpClient.dispatcher().cancelAll();
        }

        if (mCachedOkHttpClient != null) {
            // Cancel Pending Cached Request
            mCachedOkHttpClient.dispatcher().cancelAll();
        }

        if (mServerWithCachedOkHttpClient != null) {
            // Cancel Pending Cached Request
            mServerWithCachedOkHttpClient.dispatcher().cancelAll();
        }

        mRetrofit = null;
        if (mCache != null) {
            try {
                mCache.evictAll();
            } catch (IOException e) {
                Log.e(TAG, "Error cleaning http cache");
            }
        }

        mCache = null;
    }

    private HttpLoggingInterceptor provideHttpLoggingInterceptor() {

        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d("CountryService", message);
                    }
                });
        httpLoggingInterceptor.setLevel(BODY);
        return httpLoggingInterceptor;
    }

    private boolean isConnected() {
        try {
            android.net.ConnectivityManager e = (android.net.ConnectivityManager) mContext.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = e.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            Log.w(TAG, e.toString());
        }

        return false;
    }

    public static <T> T apiCallFromServer(Context context, final Class<T> service){
        if(RetrofitManager.getInstant(context).service == null) {
            RetrofitManager.getInstant(context).service =RetrofitManager.getInstant(context).apiCall(RetrofitManager.BASE_URL,RetrofitManager.FROM_SERVER).create(service);
        }
        return (T) RetrofitManager.getInstant(context).service;
    }

    public static <T> T apiCallFromCache(Context context, final Class<T> service){
        if(RetrofitManager.getInstant(context).serviceCache == null) {
            RetrofitManager.getInstant(context).serviceCache =RetrofitManager.getInstant(context).apiCall(RetrofitManager.BASE_URL,RetrofitManager.FROM_CACHE).create(service);
        }
        return (T) RetrofitManager.getInstant(context).serviceCache;
    }

    public static <T> T apiCallFromServerOrCache(Context context, final Class<T> service){
        if(RetrofitManager.getInstant(context).serviceOrCache == null) {
            RetrofitManager.getInstant(context).serviceOrCache =RetrofitManager.getInstant(context).apiCall(RetrofitManager.BASE_URL,RetrofitManager.FROM_SERVER_FIRST_CACHE_NEXT).create(service);
        }
        return (T) RetrofitManager.getInstant(context).serviceOrCache;
    }

}
