package com.skymilk.socialapp.android.presentation.screen.main.profile

sealed interface ProfileEvent {

    data object RetryData : ProfileEvent

}