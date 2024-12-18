package com.skymilk.socialapp.presentation.screen.main.profileEdit

sealed interface ProfileEditEvent {

    data object RetryData: ProfileEditEvent

    data object UpdateProfile: ProfileEditEvent

    data class UpdateName(val name: String) : ProfileEditEvent

    data class UpdateBio(val bio: String) : ProfileEditEvent

    data class UpdateImage(val byteArray: ByteArray) : ProfileEditEvent
}