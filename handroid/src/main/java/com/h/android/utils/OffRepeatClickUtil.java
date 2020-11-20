package com.h.android.utils;

/**
 * @author zhangxiaohui
 * @describe 防止重复点击
 * @date 2020/5/16
 */
public class OffRepeatClickUtil {
    private static final long MIN_DELAY_TIME = 1000;//最小点击间隔时间
    private static long lastClickTime = 0;

    public static boolean ifCanOnclick(){
        long curTimeMillis = System.currentTimeMillis();
        if(curTimeMillis - lastClickTime > MIN_DELAY_TIME){
            lastClickTime = curTimeMillis;
            return true;
        }
        return false;
    }

    public static boolean ifCannotOnclick(){
        long curTimeMillis = System.currentTimeMillis();
        if(curTimeMillis - lastClickTime > MIN_DELAY_TIME){
            lastClickTime = curTimeMillis;
            return false;
        }
        return true;
    }

}
