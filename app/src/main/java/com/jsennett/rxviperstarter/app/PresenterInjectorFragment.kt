package com.jsennett.rxviperstarter.app

import android.os.Bundle
import com.jsennett.rxviperstarter.presenter.IPresenter
import javax.inject.Inject

abstract class PresenterInjectorFragment : InjectorFragment() {
    @Inject protected lateinit var presenter: IPresenter

    abstract fun initializeView()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeView()
        presenter.onCreate(arguments, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }
}