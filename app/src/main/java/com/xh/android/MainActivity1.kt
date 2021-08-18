package com.xh.android

import android.content.Intent
import android.os.Bundle
import com.h.android.activity.HActivity
import com.h.android.utils.HToast

/**
 *2021/8/12
 *@author zhangxiaohui
 *@describe
 */
class MainActivity1 : HActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent()
        intent.putExtra("key", "返回成功")
        setResult(RESULT_OK, intent)
        finish()
    }
}