package com.skymilk.socialapp.android

import android.app.Application
import com.skymilk.socialapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class SocialApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@SocialApplication)
        }
    }
}