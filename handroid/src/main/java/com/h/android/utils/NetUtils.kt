package com.h.android.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi

/**
 *2021/9/17
 *@author zhangxiaohui
 *@describe
 */
object NetUtils {

    @RequiresApi(Build.VERSION_CODES.M)
    fun hasNetwork(var0: Context): Boolean {
        val connectivityManager = var0.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return when {
            networkCapabilities == null -> {
                false
            }
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                //当前使用移动网络
                true
            }
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                //当前使用WIFI网络
                true
            }
            else -> false
        }
    }
}