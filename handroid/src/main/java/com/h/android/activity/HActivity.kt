package com.h.android.activity

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.h.android.utils.HLog

/**
 *2020/11/27
 *@author zhangxiaohui
 *@describe
 */
open class HActivity : FragmentActivity() {

    //must call register before onResume
    //功能同startActivityForResult
    private var startActivityForResult: ActivityResultLauncher<Intent>? = null
    private var activityResultListener: ActivityResultCallback<ActivityResult>? = null

    private var requestPermissions: ActivityResultLauncher<Array<String>>? = null
    private var permissionListener: ActivityResultCallback<Map<String, Boolean>>? = null

    override fun onStart() {
        super.onStart()

        startActivityForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback { result ->
                activityResultListener?.onActivityResult(result)
            })

        requestPermissions =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
                permissionListener?.onActivityResult(map)
            }
    }

    /**
     * 跳转获取result
     */
    protected fun startActivityForResultNow(
        callback: ActivityResultCallback<ActivityResult>,
        intent: Intent
    ) {
        this.activityResultListener = callback
        startActivityForResult?.launch(intent)
    }

    /**
     * 请求权限
     */
    protected fun requestPermissionForResultNow(
        callback: ActivityResultCallback<Map<String, Boolean>>,
        vararg permissions: String
    ) {
        this.permissionListener = callback
        requestPermissions?.launch(permissions.toList().toTypedArray())
    }
}