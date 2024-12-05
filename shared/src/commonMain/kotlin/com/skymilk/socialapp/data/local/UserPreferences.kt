package com.skymilk.socialapp.data.local

import com.skymilk.socialapp.data.model.UserSettings

internal interface UserPreferences {

    suspend fun getUserData() : UserSettings

    suspend fun setUserData(userSettings: UserSettings)

}