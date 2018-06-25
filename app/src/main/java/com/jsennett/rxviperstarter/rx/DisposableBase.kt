package com.jsennett.rxviperstarter.rx

import io.reactivex.disposables.Disposable
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Base implementation of [Disposable] to perform dispose logic once.
 */
abstract class DisposableBase : Disposable{
    private val disposed = AtomicBoolean()

    abstract fun doDispose()

    override fun isDisposed(): Boolean {
        return disposed.get()
    }

    override fun dispose() {
        if (disposed.compareAndSet(false, true)) {
            doDispose()
        }
    }
}