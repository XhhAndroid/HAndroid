package com.h.android.utils.language

import java.util.*

/**
 *2021/8/12
 *@author zhangxiaohui
 *@describe
 */
enum class Languages(val code: Int, val local: Locale, val lang: String) {
    /**
     * 中文
     */
    Chinese(0, Locale.CHINESE, "zh"),

    /**
     * 英文
     */
    English(1, Locale.ENGLISH, "en"),

    /**
     * 韩语
     */
    Korean(2, Locale.KOREA, "ko"),

    /**
     * 越南语
     */
    Vietnamese(3, Locale("vi", ""), "vi");

    /**
     * 根据简写返回语言枚举
     */
    companion object {
        fun getLanguage(shortText: String):Languages {
            return when (shortText) {
                Chinese.lang -> Chinese
                English.lang -> English
                Korean.lang -> Korean
                Vietnamese.lang -> Vietnamese
                else -> English
            }
        }
    }
}