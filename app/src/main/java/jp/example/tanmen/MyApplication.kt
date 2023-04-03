package jp.example.tanmen

import android.app.Application
import timber.log.Timber.DebugTree

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        configureTimber()
    }

    private fun configureTimber() {
        if(BuildConfig.DEBUG) {
            timber.log.Timber.plant(DebugTree())
        }
    }
}