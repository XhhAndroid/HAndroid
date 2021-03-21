package com.h.android.loadding

/**
 *2021/3/21
 *@author zhangxiaohui
 *@describe
 */
interface ProgressListener {
    /**
     * 展示加载对话框
     *
     * @param notice
     */
    fun showLoadingDialog(notice: String?)

    /**
     * 结束展示对话框
     */
    fun dismissLoadingDialog()

    /**
     * 加载成功的提示
     *
     * @param notice
     */
    fun dismissLoadingDialogWithSuccess(notice: String?)

    /**
     * 加载失败的提示
     *
     * @param notice
     */
    fun dismissLoadingDialogWithFail(notice: String?)

    /**
     * 是否正在展示加载对话框
     *
     * @return
     */
    fun isShowLoading(): Boolean
}