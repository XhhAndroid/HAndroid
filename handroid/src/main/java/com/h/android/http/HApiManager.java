package com.h.android.http;

/**
 * 2020/11/20
 *
 * @author zhangxiaohui
 * @describe
 */
public class HApiManager {
    private static HApiManager apiManager;

    public static HApiManager getInstance(){
        if(apiManager == null){
            apiManager = new HApiManager();
        }
        return apiManager;
    }


    public <T> T getApiService(Class<T> apiClazz) {
        return Hhttp.getApiService(apiClazz);
    }
}
