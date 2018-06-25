package com.jsennett.rxviperstarter.app

import android.content.Context
import android.util.Log

abstract class InjectorFragment : FragmentBase() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val application = context.applicationContext as? ApplicationBase
        if (application?.injector?.inject(this) != true) {
            Log.w("InjectorFragment", "Unable to inject fragment ${this::class.java.name}")
        }
    }
}