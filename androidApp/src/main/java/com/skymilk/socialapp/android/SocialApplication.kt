package com.skymilk.socialapp.android

import android.app.Application
import com.skymilk.socialapp.android.di.appModule
import com.skymilk.socialapp.di.getSharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SocialApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule + getSharedModule())
            androidContext(this@SocialApplication)
        }
    }
}