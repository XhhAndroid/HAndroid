package com.h.android.http;

import com.h.android.http.annotation.NetworkInterceptor;
import com.h.android.http.interceptor.BaseUrlProvider;

import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 2020/11/20
 *
 * @author zhangxiaohui
 * @describe
 */
public class Hhttp {

    private static final ConcurrentHashMap<Class, Object> API_MAP = new ConcurrentHashMap<>();

    /**
     * get api
     *
     * @param apiClazz
     * @param <T>
     * @return
     */
    public static <T> T getApiService(Class<T> apiClazz) {
        Object api = API_MAP.get(apiClazz);
        if (api == null) {
            try {
                API_MAP.put(apiClazz, api = createApiService(apiClazz));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return (T) api;
    }

    /**
     * remove api service
     *
     * @param apiClazz
     */
    public static void clearApiService(Class apiClazz) {
        API_MAP.remove(apiClazz);
    }

    /**
     * remove all api service
     */
    public static void clearAllApiService() {
        API_MAP.clear();
    }


    /**
     * @param apiClazz
     * @param <T>
     * @return
     */
    private static <T> T createApiService(Class<T> apiClazz)
            throws IllegalAccessException, InstantiationException, IllegalArgumentException {
        OkHttpClientBuilder ohcb = new OkHttpClientBuilder();

        BaseUrl baseUrlAnnotation = apiClazz.getAnnotation(BaseUrl.class);
        BaseUrlProvider baseUrlProviderAnnotation = apiClazz.getAnnotation(BaseUrlProvider.class);
        if (baseUrlAnnotation == null && baseUrlProviderAnnotation == null) {
            throw new IllegalArgumentException("please use  BaseUrl or BaseUrlProvider Annotation to" + apiClazz);
        }
        String baseUrl;
        if (baseUrlProviderAnnotation != null) {
            baseUrl = baseUrlProviderAnnotation.value().newInstance().getBaseUrl(apiClazz);
        } else {
            baseUrl = baseUrlAnnotation.value();
        }

        //拦截器可选
        com.h.android.http.annotation.Interceptor interceptorAnnotation = apiClazz.getAnnotation(com.h.android.http.annotation.Interceptor.class);
        if (interceptorAnnotation != null) {
            Class<? extends Interceptor>[] value = interceptorAnnotation.value();
            if (value != null) {
                for (Class<? extends Interceptor> in : value) {
                    Interceptor interceptor = in.newInstance();
                    ohcb.addInterceptor(interceptor);
                }
            }
        }

        //网络拦截器
        NetworkInterceptor networkInterceptorAnnotation = apiClazz.getAnnotation(NetworkInterceptor.class);
        if (networkInterceptorAnnotation != null) {
            Class<? extends Interceptor>[] value = networkInterceptorAnnotation.value();
            if (value != null) {
                for (Class<? extends Interceptor> in : value) {
                    Interceptor interceptor = in.newInstance();
                    ohcb.addNetworkInterceptor(interceptor);
                }
            }
        }

        // 初始化Retrofit
        return new Retrofit.Builder()
                .client(ohcb.build())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(apiClazz);
    }
}
