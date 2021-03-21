package com.h.android.rx

import com.h.android.rx.transformer.UILifeTransformer
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import org.reactivestreams.Publisher

/**
 *2021/3/21
 *@author zhangxiaohui
 *@describe
 */
abstract class UILifeTransformerImpl<T> : UILifeTransformer<T>,
    ObservableTransformer<T, T>,
    FlowableTransformer<T, T>,
    MaybeTransformer<T, T>,
    CompletableTransformer {

    /**
     * Applies a function to the upstream Observable and returns an ObservableSource with
     * optionally different element type.
     * @param upstream the upstream Observable instance
     * @return the transformed ObservableSource instance
     */
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onSubscribe() }
            .doOnNext { t -> onNext(t) }
            .doOnError { throwable -> onError(throwable) }
            .doOnComplete { onComplete() }
            .doOnDispose { onCancel() }
    }

    /**
     * Applies a function to the upstream Flowable and returns a Publisher with
     * optionally different element type.
     * @param upstream the upstream Flowable instance
     * @return the transformed Publisher instance
     */
    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onSubscribe() }
            .doOnNext { t -> onNext(t) }
            .doOnError { throwable -> onError(throwable) }
            .doOnComplete { onComplete() }
            .doOnCancel { onCancel() }
    }

    /**
     * Applies a function to the upstream Maybe and returns a MaybeSource with
     * optionally different element type.
     * @param upstream the upstream Maybe instance
     * @return the transformed MaybeSource instance
     */
    override fun apply(upstream: Maybe<T>): MaybeSource<T> {
        return upstream
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onSubscribe() }
            .doOnSuccess { t -> onNext(t) }
            .doOnError { throwable -> onError(throwable) }
            .doOnComplete { onComplete() }
            .doOnDispose { onCancel() }
    }

    /**
     * Applies a function to the upstream Completable and returns a CompletableSource.
     * @param upstream the upstream Completable instance
     * @return the transformed CompletableSource instance
     */
    override fun apply(upstream: Completable): CompletableSource {
        return upstream.observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onSubscribe() }
            .doOnError { throwable -> onError(throwable) }
            .doOnComplete { onComplete() }
            .doOnDispose { onCancel() }
    }
}