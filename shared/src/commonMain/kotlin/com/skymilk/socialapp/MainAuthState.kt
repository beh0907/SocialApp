package com.skymilk.socialapp

import com.skymilk.socialapp.store.data.model.UserSettings

sealed interface MainAuthState {

    data object Initial : MainAuthState

    data object Loading : MainAuthState

    data class Success(val currentUser: UserSettings) : MainAuthState

}