package com.h.android.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.lang.ref.WeakReference

/**
 * 键盘控制
 */
object KeyboardUtils {

    /**
     * 隐藏软键盘
     */
    fun hideKeyboard(act: Context?, v: View?){
        v?.run {
            val inputMethodManager:InputMethodManager= act?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(windowToken,0)
        }
    }


    /**
     * 显示软键盘
     */
    fun showSoftKeyBoard(act: Context?, v: View?) {
        if(act == null){
            return
        }
        val weakReference = WeakReference(act)
        try {
            v?.requestFocus()
            val imm = weakReference.get()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(v, InputMethodManager.SHOW_FORCED)
        } catch (e: Exception) {
        }
    }
}