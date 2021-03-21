package com.h.android.rx.transformer

import android.text.TextUtils
import com.h.android.loadding.ProgressListener
import com.h.android.loadding.ProgressProvider
import com.h.android.rx.UILifeTransformerImpl

/**
 *2021/3/21
 *@author zhangxiaohui
 *@describe
 */
class ProgressHUDTransformerImpl<T> : UILifeTransformerImpl<T> {
    protected var progressListener: ProgressListener? = null
    protected var loadingNotice: String? = null
    protected var errorNotice: String? = null
    protected var successNotice: String? = null

    /**
     * @param progressHUD
     * @param loadingNotice 可空
     * @param errorNotice   如果为空,直接展示默认的error,否则展示errorNotice
     * @param successNotice 空 不展示正确对勾符号
     */
    constructor(
        progressHUD: ProgressListener?,
        loadingNotice: String?,
        errorNotice: String?,
        successNotice: String?
    ) {
        this.progressListener = progressHUD
        this.loadingNotice = loadingNotice
        this.errorNotice = errorNotice
        this.successNotice = successNotice
    }

    /**
     * builder模式
     */
    class Builder {
        var progress: ProgressListener? = null
        var loadingNotice: String? = null
        var errorNotice: String? = null
        var successNotice: String? = null

        constructor(progressProvider: ProgressProvider<*>) {
            progress = progressProvider.progressView()
        }

        constructor(progressHUD: ProgressListener) {
            this.progress = progressHUD
        }

        fun setLoadingNotice(loadingNotice: String?): Builder {
            this.loadingNotice = loadingNotice
            return this
        }

        fun setErrorNotice(errorNotice: String?): Builder {
            this.errorNotice = errorNotice
            return this
        }

        fun setSuccessNotice(successNotice: String?): Builder {
            this.successNotice = successNotice
            return this
        }

        fun <T> build(): ProgressHUDTransformerImpl<T> {
            return ProgressHUDTransformerImpl(progress, loadingNotice, errorNotice, successNotice)
        }
    }

    /**
     * 开始执行
     */
    override fun onSubscribe() {
        if (progressListener != null) {
            progressListener!!.showLoadingDialog(loadingNotice)
        }
    }

    /**
     * 接收到数据(可能多次)执行
     *
     * @param t
     */
    override fun onNext(t: T?) {
        if (progressListener != null) {
            progressListener!!.dismissLoadingDialogWithSuccess(successNotice)
        }
    }

    /**
     * 执行结束
     */
    override fun onComplete() {
        if (progressListener != null && progressListener!!.isShowLoading()) {
            progressListener!!.dismissLoadingDialogWithSuccess(successNotice)
        }
    }

    /**
     * 执行失败
     *
     * @param throwable
     */
    override fun onError(throwable: Throwable?) {
        if (progressListener != null) {
            if (TextUtils.isEmpty(errorNotice)) {
                var parseErrorNotice = ""
                try {
                    parseErrorNotice = throwable?.message ?: "rx error"
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                progressListener!!.dismissLoadingDialogWithFail(parseErrorNotice)
            } else {
                progressListener!!.dismissLoadingDialogWithFail(errorNotice)
            }
        }
    }

    /**
     * 执行取消
     */
    override fun onCancel() {
        if (progressListener != null) {
            progressListener!!.dismissLoadingDialog()
        }
    }
}