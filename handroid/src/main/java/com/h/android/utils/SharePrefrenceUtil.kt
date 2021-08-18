package com.h.android.utils

import android.app.Application
import android.content.Context
import com.h.android.HAndroid

/**
 * @author zhangxiaohui
 * SharePrefrence模式管理类
 * google的替代品DataStore
 */
object SharePrefrenceUtil {
    private val tAG: String
        private get() = HAndroid.getApplication().packageName


    fun setTextValue(key: String?, value: String?) {
        val sp = HAndroid.getApplication().getSharedPreferences(tAG, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * 同步保存
     *
     * @param key
     * @param value
     */
    fun setTextValueSync(key: String?, value: String?): Boolean {
        val sp = HAndroid.getApplication().getSharedPreferences(tAG, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(key, value)
        return editor.commit()
    }

    fun setTextValueSync(key: String?, value: Boolean) {
        val sp = HAndroid.getApplication().getSharedPreferences(tAG, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun setTextValue(key: String?, value: Boolean) {
        val sp = HAndroid.getApplication().getSharedPreferences(tAG, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun setTextValue(key: String?, value: Long) {
        val sp = HAndroid.getApplication().getSharedPreferences(tAG, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun setTextValue(key: String?, value: Int) {
        val sp = HAndroid.getApplication().getSharedPreferences(tAG, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun setTextValueSync(key: String?, value: Int): Boolean {
        val sp = HAndroid.getApplication().getSharedPreferences(tAG, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putInt(key, value)
        return editor.commit()
    }

    fun getTextValueBooble(key: String?): Boolean {
        val sp = HAndroid.getApplication().getSharedPreferences(tAG, Context.MODE_PRIVATE)
        return sp.getBoolean(key, false)
    }

    fun getTextValueBoobleTrue(key: String?): Boolean {
        val sp = HAndroid.getApplication().getSharedPreferences(tAG, Context.MODE_PRIVATE)
        return sp.getBoolean(key, true)
    }

    fun getTextValue(key: String?): String? {
        val sp = HAndroid.getApplication().getSharedPreferences(tAG, Context.MODE_PRIVATE)
        return sp.getString(key, "")
    }

    fun getTextValueLong(key: String?): Long {
        val sp = HAndroid.getApplication().getSharedPreferences(tAG, Context.MODE_PRIVATE)
        return sp.getLong(key, 0)
    }

    fun getTextValueInteger(key: String?): Int {
        val sp = HAndroid.getApplication().getSharedPreferences(tAG, Context.MODE_PRIVATE)
        return getTextValueInteger(key, 0)
    }

    fun getTextValueInteger(key: String?, defaultValue: Int): Int {
        val sp = HAndroid.getApplication().getSharedPreferences(tAG, Context.MODE_PRIVATE)
        return sp.getInt(key, defaultValue)
    }

    fun clearSingleSharePrefrence(key: String?) {
        val mSPreferences = HAndroid.getApplication().getSharedPreferences(tAG, Context.MODE_PRIVATE)
        mSPreferences.edit().remove(key).apply()
    }

    /**
     * 清空缓存
     */
    fun clearSharePrefrence() {
        val mSPreferences = HAndroid.getApplication().getSharedPreferences(tAG, Context.MODE_PRIVATE)
        mSPreferences.edit().clear().apply()
    }
}