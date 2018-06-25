package com.jsennett.rxviperstarter.presenter

import android.os.Bundle
import com.jsennett.rxviperstarter.rx.DisposableBase
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.Single.create
import io.reactivex.disposables.CompositeDisposable

abstract class PresenterBase : IPresenter {
    protected var pauseDisposables = CompositeDisposable()
    protected var destroyDisposables = CompositeDisposable()

    override fun onCreate(arguments: Bundle?, savedInstanceState: Bundle?) {}

    override fun onResume() {}

    override fun onPause() {
        pauseDisposables.dispose()
        pauseDisposables = CompositeDisposable()
    }

    override fun onDestroy() {
        destroyDisposables.dispose()
        destroyDisposables = CompositeDisposable()
    }

    override fun onSaveInstanceState(outState: Bundle) {}

    companion object {
        fun <T> safe(observable: Observable<T>?): Observable<T> {
            if (observable == null) {
                return Observable.empty<T>()
            }

            return observable
        }

        fun <T> safe(single: Single<T>?): Single<T> {
            if (single == null) {
                return create { e ->
                    e.setDisposable(object : DisposableBase() {
                        override fun doDispose() {}
                    })
                }
            }

            return single
        }

        fun <T> safe(maybe: Maybe<T>?): Maybe<T> {
            if (maybe == null) {
                return Maybe.empty<T>()
            }

            return maybe
        }
    }
}