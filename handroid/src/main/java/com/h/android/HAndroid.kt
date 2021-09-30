package com.h.android

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.h.android.listener.IToastListener
import com.h.android.loadding.ProgressHUDFactory.Companion.get
import com.h.android.loadding.ProgressHUDFactory.ProgressHUDProvider
import com.h.android.rx.transformer.ProgressHUDTransformerImpl
import com.h.android.rx.transformer.UIErrorTransformer
import com.h.android.stack.AndroidActivityStackProvider
import com.h.android.utils.HLog
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import java.util.*

/**
 * 2020/11/20
 *
 * @author zhangxiaohui
 * @describe
 */
object HAndroid {
    private var application: Application? = null
    var activityStackProvider: AndroidActivityStackProvider? = null
        private set
    var isDebug = false
        private set

    fun init(builder: Builder) {
        if (application == null) {
            synchronized(HAndroid::class.java) {
                if (application == null) {
                    application = builder.application
                    activityStackProvider = AndroidActivityStackProvider(application)
                    get().progressHUDProvider = builder.progressHUDProvider
                }
            }
        }
    }

    fun getApplication(): Application {
        if (application == null) {
            throw NullPointerException("you need call HAndroid.init function in application's onCreate")
        }
        return application!!
    }

    /**
     * 绑定loading
     *
     * @param lifecycleOwner
     * @param <T>
     * @return
    </T> */
    fun <T> bindToProgressHud(lifecycleOwner: LifecycleOwner): ProgressHUDTransformerImpl<T> {
        val progressHUD = get().getProgressHUD(lifecycleOwner) ?: throw NullPointerException("progressHUD is null")
        return ProgressHUDTransformerImpl.Builder(progressHUD).build()
    }

    /**
     * 绑定错误提示
     */
    fun <R> bindError(): UIErrorTransformer<R> {
        return UIErrorTransformer(consumerLocal)
    }

    /**
     * 自动取消rx流
     */
    fun <T> autoDisposable(lifecycle: LifecycleOwner): AutoDisposeConverter<T> {
        return autoDisposable(lifecycle, Lifecycle.Event.ON_DESTROY)
    }

    fun <T> autoDisposable(lifecycle: LifecycleOwner, untilEvent: Lifecycle.Event): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycle, untilEvent))
    }

    fun cancelDisposable(disposable: Disposable?){
        disposable?.let {
            if(!it.isDisposed){
                it.dispose()
            }
        }
    }

    fun toast(): IToastListener? {
        if(iToast == null){
            HLog.e("must init addToast()")
            return null
        }
        return iToast!!
    }

    //不调用addErrorHandler()时默认在此处处理
    private var consumerLocal: Consumer<Throwable> = Consumer<Throwable> { thr ->
        thr.message?.let { s ->
            toast()?.showToastFail(s)
        }
    }

    private var iToast: IToastListener? = null

    class Builder(application: Application) {
        var application: Application = Objects.requireNonNull(application)
        var progressHUDProvider: ProgressHUDProvider? = null
        var errorConvertFunction: Function<Throwable, String> = Function { throwable -> throwable.message!! }

        fun addProgressProvider(progressHUDProvider: ProgressHUDProvider?): Builder {
            this.progressHUDProvider = progressHUDProvider
            return this
        }

        /**
         * 来自function异常
         */
        fun addErrorConvertFunction(errorConvertFunction: Function<Throwable, String>): Builder {
            this.errorConvertFunction = Objects.requireNonNull(errorConvertFunction)
            return this
        }

        /**
         * 来自doError异常
         */
        fun addErrorHandler(consumer: Consumer<Throwable>): Builder {
            consumerLocal = consumer
            return this
        }

        fun addToast(iToastListener: IToastListener):Builder {
            iToast = iToastListener
            return this
        }

        fun setDebug(debug: Boolean): Builder {
            isDebug = debug
            return this
        }

    }
}