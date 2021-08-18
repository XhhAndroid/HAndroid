package com.h.android.utils.language

import android.content.Context
import android.os.Build
import android.text.TextUtils
import com.h.android.utils.language.Languages.Companion.getLanguage

object LanguageUtil {
    private const val LANGUAGE_TEXT = "language_text"

    /**
     * 更改应用语言
     *
     * @param context
     * @param Languages      语言地区
     * @param persistence 是否持久化
     */
    fun changeAppLanguage(context: Context, language: Languages, persistence: Boolean) {
        val resources = context.resources
        val metrics = resources.displayMetrics
        val configuration = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(language.local)
        } else {
            configuration.locale = language.local
        }
        resources.updateConfiguration(configuration, metrics)
        if (persistence) {
            saveLanguageSetting(context, language)
        }
    }

    private fun saveLanguageSetting(context: Context, languages: Languages) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(LANGUAGE_TEXT, languages.lang)
        editor.apply()
    }

    fun getLanguageSetting(context: Context): Languages {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val languageText = preferences.getString(LANGUAGE_TEXT, null)
        return if (TextUtils.isEmpty(languageText)) {
            Languages.English
        } else {
            getLanguage(languageText!!)
        }
    }

    /**
     * 获取当前系统语言
     *
     * @param mContext
     * @return
     */
    fun getSystemLanguage(mContext: Context): String {
        val locale = mContext.resources.configuration.locale
        return locale.language // 获得语言码
    }
}