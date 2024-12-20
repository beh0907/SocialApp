package com.skymilk.socialapp.di

import com.skymilk.socialapp.store.data.local.IosUserPreferences
import com.skymilk.socialapp.store.data.local.UserPreferences
import com.skymilk.socialapp.store.data.local.createDataStore
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::IosUserPreferences) { bind<UserPreferences>() }

    //ios는 별도 데이터스토어 생성 함수 호출
    singleOf(::createDataStore)
}