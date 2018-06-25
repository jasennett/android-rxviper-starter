package com.jsennett.rxviperstarter.app

import android.os.Bundle
import com.jsennett.rxviperstarter.presenter.IPresenter
import javax.inject.Inject

abstract class PresenterInjectorActivity : InjectorActivity() {
    @Inject protected lateinit var presenter: IPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeView()
        presenter.onCreate(intent?.extras, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }

    protected abstract fun initializeView()
}