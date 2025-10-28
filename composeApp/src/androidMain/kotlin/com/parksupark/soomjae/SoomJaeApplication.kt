package com.parksupark.soomjae

import SoomJae.composeApp.BuildConfig
import android.app.Application
import com.parksupark.soomjae.security.SignatureLogger

class SoomjaeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.isDebug) {
            SignatureLogger.logAppSignatures(this)
        }
    }
}
