package com.skymilk.socialapp.store.data.local

import com.skymilk.socialapp.store.data.model.UserSettings

internal interface UserPreferences {

    suspend fun getUserData(): UserSettings

    suspend fun setUserData(userSettings: UserSettings)

}