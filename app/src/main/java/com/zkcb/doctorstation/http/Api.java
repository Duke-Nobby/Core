package com.zkcb.doctorstation.http;

import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.readystatesoftware.chuck.ChuckInterceptor;
import com.zkcb.doctorstation.util.AppProfile;
import com.zkcb.doctorstation.util.LogUtil;
import com.zkcb.doctorstation.util.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.zkcb.doctorstation.BuildConfig.API_HOST;


/**
 * Created by Sunflower on 2015/11/4.
 */
public class Api {

    /**
     * 服务器地址
     */
    // 请求公共部分
    private static String BASE_URL = API_HOST;
    private static ApiService service;
    private static Retrofit retrofit;

    public static ApiService getService() {
        if (service == null) {
            service = getRetrofit().create(ApiService.class);
        }
        return service;
    }

    /**
     * 拦截器  给所有的请求添加消息头
     */
    private static Interceptor mInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {


            Request request = chain.request()
                    .newBuilder()
                    .build();
            return chain.proceed(request);
        }
    };

    /**
     * 缓存拦截器
     */
    private static Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //设置 请求的缓存
            Request request = chain.request();
            Response response = chain.proceed(request);
            if (!NetworkUtils.isAvailable(AppProfile.getContext())) {
                // 没有网络走缓存
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();

            } else {
                String serverCache = response.header("Cache-Control");
                if (TextUtils.isEmpty(serverCache)) {
                    String cacheControl = request.cacheControl().toString();
                    if (TextUtils.isEmpty(cacheControl)) {
                        return response.newBuilder()
                                .removeHeader("Cache-Control")
                                .build();
                    } else {
                        return response.newBuilder()
                                .addHeader("Cache-Control", cacheControl)
                                .build();
                    }
                }
            }
            return chain.proceed(request);
        }
    };

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            // log拦截器  打印所有的log
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            File cacheFile = new File(AppProfile.getContext().getCacheDir(), "cache");
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 10); //50Mb

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    //添加缓存拦截器
                    .addNetworkInterceptor(interceptor)
                    .addInterceptor(mInterceptor)
                    //添加ChuckInterceptor
                    .addInterceptor(new ChuckInterceptor(AppProfile.getContext()))
                    .cache(cache)
                    .build();

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void changeApiBaseUrl(String newApiBaseUrl) {
        BASE_URL = newApiBaseUrl;
    }

    public <T> Observable<T> get(String url, Map parameters) {
        return getService().executeGet(url, parameters)
                .compose(schedulersTransformer())
                .compose(transformer())
                .flatMap(new Function<T, Observable<T>>() {
                    @Override
                    public Observable<T> apply(T t) throws Exception {
                        return flatResponse(t);
                    }
                });
    }


    public <T> Observable<T> post(String url, Map<String, String> parameters) {
        return getService().executePost(url, parameters)
                .compose(schedulersTransformer())
                .compose(transformer())
                .flatMap(new Function<T, Observable<T>>() {
                    @Override
                    public Observable<T> apply(T t) throws Exception {
                        return flatResponse(t);
                    }
                });
    }

    private ObservableTransformer schedulersTransformer() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.
                        subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private <T> ObservableTransformer<BaseResponse, T> transformer() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.flatMap(new Function<T, Observable<T>>() {
                    @Override
                    public Observable<T> apply(T t) throws Exception {
                        return flatResponse(t);
                    }
                });
            }

        };
    }


    private <T> Observable<T> flatResponse(final T response) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                if (response != null) {
                    if (!e.isDisposed()) {
                        e.onNext(response);
                        LogUtil.e("flatResponse", "");
                    }
                } else {
                    LogUtil.e("subscribe", "subscribe");
                    if (!e.isDisposed()) {
                        e.onError(new Exception());
                    }
                    return;
                }
                if (!e.isDisposed()) {
                    e.onComplete();
                }

            }
        });
    }


   /* public <T> Observable<T> post(String url, Map<String, String> parameters) {
        return service.executePost(url, parameters)
                .compose(schedulersTransformer())
                .compose(transformer());
    }

    public <T> Observable<T> json(String url, RequestBody jsonStr) {
        return service.json(url, jsonStr)
                .compose(schedulersTransformer())
                .compose(transformer());
    }

    public void upload(String url, RequestBody requestBody, Subscriber<ResponseBody> subscriber) {
        service.upLoadFile(url, requestBody)
                .compose(schedulersTransformer())
                .compose(transformer());
    }

    public void download(String url, final CallBack callBack) {
        return service.downloadFile(url)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(new DownSubscriber<ResponseBody>(callBack));
    }*/


}
