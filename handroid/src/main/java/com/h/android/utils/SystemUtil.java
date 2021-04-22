package com.h.android.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.util.Locale;

/**
 * @author zhangxiaohui
 * @describe
 * @date 2019/8/9
 */
public class SystemUtil {

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        try {
            return Locale.getDefault().getLanguage();
        } catch (Exception e) {
            return "default";
        }
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        try {
            return Build.VERSION.RELEASE;
        } catch (Exception e) {
            return "default";
        }
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        try {
            return Build.MODEL;
        } catch (Exception e) {
            return "default";
        }
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        try {
            return Build.BRAND;
        } catch (Exception e) {
            return "default";
        }
    }


    /*
     * 判断设备 是否使用代理上网
     * */
    public static boolean isWifiProxy(Context context) {
        try {
            final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
            String proxyAddress;
            int proxyPort;
            if (IS_ICS_OR_LATER) {
                proxyAddress = System.getProperty("http.proxyHost");
                String portStr = System.getProperty("http.proxyPort");
                proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
            } else {

                proxyAddress = android.net.Proxy.getHost(context);
                proxyPort = android.net.Proxy.getPort(context);
            }
            return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
