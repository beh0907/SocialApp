package com.skymilk.socialapp.android

import android.app.Application
import com.skymilk.socialapp.di.appModule
import com.skymilk.socialapp.di.getSharedModule
import com.skymilk.socialapp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SocialApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}