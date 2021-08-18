package com.h.android.utils

/**
 * @author zhangxiaohui
 * @describe 防止重复点击
 * @date 2020/5/16
 */
object OffRepeatClickUtil {
    private const val MIN_DELAY_TIME: Long = 800 //最小点击间隔时间
    private var lastClickTime: Long = 0
    fun ifCannotOnclick(): Boolean {
        val curTimeMillis = System.currentTimeMillis()
        if (curTimeMillis - lastClickTime > MIN_DELAY_TIME) {
            lastClickTime = curTimeMillis
            return false
        }
        return true
    }

    /**
     * 一定时间内不会多次触发
     * @param delay 单位毫秒
     * @return
     */
    fun ifCannotOnclick(delay: Long): Boolean {
        val curTimeMillis = System.currentTimeMillis()
        if (curTimeMillis - lastClickTime > delay) {
            lastClickTime = curTimeMillis
            return false
        }
        return true
    }
}