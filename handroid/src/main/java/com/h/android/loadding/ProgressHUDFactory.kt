package com.h.android.loadding

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 *2021/3/21
 *@author zhangxiaohui
 *@describe
 */
class ProgressHUDFactory private constructor(): LifecycleObserver {
    var progressHUDProvider: ProgressHUDProvider? = null
    var progressViewMap : ConcurrentHashMap<LifecycleOwner, ProgressListener?> = ConcurrentHashMap<LifecycleOwner, ProgressListener?>()

    interface ProgressHUDProvider {
        fun onCreateProgressHUD(lifecycleOwner: LifecycleOwner?): ProgressListener?
    }

    companion object{
        private var progressHUDFactory : ProgressHUDFactory? = null
        get(){
            if(field == null){
                progressHUDFactory = ProgressHUDFactory()
            }
            return field
        }

        fun get(): ProgressHUDFactory{
            //细心的小伙伴肯定发现了，这里不用getInstance作为为方法名，是因为在伴生对象声明时，内部已有getInstance方法，所以只能取其他名字
            return progressHUDFactory!!
        }
    }

    /**
     * 移除
     *
     * @param key
     */
    private fun removeProgressHud(key: LifecycleOwner) {
        val progressHUD = progressViewMap[key]
        if (progressHUD != null && progressHUD.isShowLoading()) {
            progressHUD.dismissLoadingDialog()
        }
        progressViewMap.remove(key)
    }

    /**
     * 获取 hud
     *
     * @param lifecycleOwner
     * @return
     */
    fun getProgressHUD(lifecycleOwner: LifecycleOwner): ProgressListener? {
        var progressHUD =
            progressViewMap[Objects.requireNonNull(lifecycleOwner, "lifecycleOwner is null")]
        if (progressHUD == null) {
            //add Observer
            lifecycleOwner.lifecycle.removeObserver(this)
            lifecycleOwner.lifecycle.addObserver(this)
            progressHUD = progressHUDProvider?.onCreateProgressHUD(lifecycleOwner)
            progressHUD?.let{
                progressViewMap[lifecycleOwner] = it
            }
        }
        return progressHUD
    }
}