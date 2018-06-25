package com.jsennett.rxviperstarter.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

abstract class InjectorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = application as ApplicationBase
        if (!application.injector.inject(this)) {
            Log.w("InjectorActivity", "Unable to inject activity ${this::class.java.name}")
        }
    }
}