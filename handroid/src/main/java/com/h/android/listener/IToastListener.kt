package com.h.android.listener

/**
 *2021/9/17
 *@author zhangxiaohui
 *@describe
 */
interface IToastListener {
    fun showToast(text: String)
    fun showToastSuccess(text: String)
    fun showToastFail(text: String)
}