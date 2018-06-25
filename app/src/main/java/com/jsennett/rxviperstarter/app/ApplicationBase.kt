package com.jsennett.rxviperstarter.app

import android.app.Application
import android.os.StrictMode
import com.jsennett.rxviperstarter.dagger.Injector
import javax.inject.Inject

abstract class ApplicationBase : Application() {
    @Inject lateinit var injector: Injector

    protected abstract val isDebugMode: Boolean

    protected abstract fun injectDependencies()

    override fun onCreate() {
        super.onCreate()

        // Set strict mode while in debug builds to detect dangerous behavior
        if (isDebugMode) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .penaltyDeath()
                    .build())
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectActivityLeaks()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build())
        }

        injectDependencies()
    }
}