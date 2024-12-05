package com.skymilk.socialapp.data.local

import androidx.datastore.core.DataStore
import com.skymilk.socialapp.data.model.UserSettings
import kotlinx.coroutines.flow.first

internal class AndroidUserPreferences(
    private val dataStore: DataStore<UserSettings>
) : UserPreferences {
    override suspend fun getUserData(): UserSettings {
        return dataStore.data.first()
    }

    override suspend fun setUserData(userSettings: UserSettings) {
        dataStore.updateData { userSettings }
    }
}