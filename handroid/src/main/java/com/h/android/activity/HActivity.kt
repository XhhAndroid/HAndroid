package com.h.android.activity

import android.content.Intent
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity

/**
 *2020/11/27
 *@author zhangxiaohui
 *@describe
 */
open class HActivity : AppCompatActivity(){

    @CallSuper
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @CallSuper
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}