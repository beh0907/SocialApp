package com.skymilk.socialapp.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.skymilk.socialapp.data.model.UserSettings
import com.skymilk.socialapp.util.Constants
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask

internal class IosUserPreferences(
    private val dataStore: DataStore<Preferences>
) : UserPreferences {
    override suspend fun getUserData(): UserSettings {
        return dataStore.data
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
            val documentDirectory: NSURL = NSFileManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )
            (requireNotNull(documentDirectory).path + "/${Constants.PREFERENCE_FILE_NAME}").toPath()
        }
    )
}