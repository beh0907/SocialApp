package com.skymilk.socialapp.di

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.skymilk.socialapp.data.local.AndroidUserPreferences
import com.skymilk.socialapp.data.local.UserPreferences
import com.skymilk.socialapp.util.UserSettingsSerializer
import org.koin.dsl.module

actual val platformModule = module {
    single<UserPreferences> { AndroidUserPreferences(get()) }

    single {
        DataStoreFactory.create(
            serializer = UserSettingsSerializer,
            produceFile = { get<Context>().dataStoreFile("user_settings") }
        )
    }
}