package com.jsennett.rxviperstarter.presenter

import android.os.Bundle

interface IPresenter {
    fun onCreate(arguments: Bundle?, savedInstanceState: Bundle?)
    fun onResume()
    fun onPause()
    fun onDestroy()
    fun onSaveInstanceState(outState: Bundle)
}