package com.h.android.http;

/**
 * 2020/11/20
 *
 * @author zhangxiaohui
 * @describe
 */
public class ApiManager {
    private static ApiManager apiManager;

    public static ApiManager getInstance(){
        if(apiManager == null){
            apiManager = new ApiManager();
        }
        return apiManager;
    }


    public <T> T getApiService(Class<T> apiClazz) {
        return Hhttp.getApiService(apiClazz);
    }
}
