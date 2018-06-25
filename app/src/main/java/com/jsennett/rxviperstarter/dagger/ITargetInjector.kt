package com.jsennett.rxviperstarter.dagger

interface ITargetInjector<T> {
    fun inject(target: T)
}