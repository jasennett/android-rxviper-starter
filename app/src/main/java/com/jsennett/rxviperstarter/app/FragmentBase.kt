package com.jsennett.rxviperstarter.app

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jsennett.rxviperstarter.util.ResettableLazy
import com.jsennett.rxviperstarter.util.ResettableLazyManager
import com.jsennett.rxviperstarter.util.resettableLazy

abstract class FragmentBase : Fragment() {
    private val resettableLazyManager = ResettableLazyManager()

    abstract fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        resettableLazyManager.reset()
        return createView(inflater, container, savedInstanceState)
    }

    fun <T : View> bindView(id: Int): ResettableLazy<T> {
        return resettableLazy(resettableLazyManager) {
            @Suppress("UNCHECKED_CAST")
            view!!.findViewById<T>(id)
        }
    }
}