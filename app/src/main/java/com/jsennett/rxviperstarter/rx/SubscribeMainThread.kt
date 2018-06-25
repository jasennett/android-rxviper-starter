package com.jsennett.rxviperstarter.rx

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

fun <T> Observable<T>.subscribeMainThread(subscription: (T) -> Unit): Disposable {
    return this
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscription)
}

fun <T> Maybe<T>.subscribeMainThread(subscription: (T) -> Unit): Disposable {
    return this
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscription)
}

fun <T> Single<T>.subscribeMainThread(subscription: (T) -> Unit): Disposable {
    return this
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscription)
}