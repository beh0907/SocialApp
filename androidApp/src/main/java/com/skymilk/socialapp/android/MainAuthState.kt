package com.skymilk.socialapp.android

import com.skymilk.socialapp.data.model.UserSettings

sealed interface MainAuthState {

    data object Initial : MainAuthState

    data object Loading : MainAuthState

    data class Success(val currentUser: UserSettings) : MainAuthState

}