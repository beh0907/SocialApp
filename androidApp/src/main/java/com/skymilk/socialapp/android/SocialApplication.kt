package com.skymilk.socialapp.android

import android.app.Application
import com.skymilk.socialapp.android.di.appModule
import org.koin.core.context.startKoin

class SocialApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
        }
    }
}