package com.skymilk.socialapp.store.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.skymilk.socialapp.store.data.model.UserSettings
import com.skymilk.socialapp.util.Constants.PREFERENCE_FILE_NAME
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.first
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

internal class IosUserPreferences(
    private val dataStore: DataStore<UserSettings>
) : UserPreferences {
    override suspend fun getUserData(): UserSettings {
        return dataStore.data.first()
    }

    override suspend fun setUserData(userSettings: UserSettings) {
        dataStore.updateData { userSettings }
    }
}

@OptIn(ExperimentalForeignApi::class)
internal fun createDataStore(): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        corruptionHandler = null,
        migrations = listOf(),
        produceFile = {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            (requireNotNull(documentDirectory).path + "/$PREFERENCE_FILE_NAME").toPath()
        }
    )
}