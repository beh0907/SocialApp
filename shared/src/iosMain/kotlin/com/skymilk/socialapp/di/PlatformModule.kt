package com.skymilk.socialapp.di

import com.skymilk.socialapp.data.local.IosUserPreferences
import com.skymilk.socialapp.data.local.UserPreferences
import com.skymilk.socialapp.data.local.createDataStore
import org.koin.dsl.module

actual val platformModule = module {
    single<UserPreferences> { IosUserPreferences(get()) }

    //ios는 별도 데이터스토어 생성 함수 호출
    single { createDataStore() }
}