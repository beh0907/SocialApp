package com.skymilk.socialapp.android.presentation.screen.main.profileEdit.state

data class ProfileEditUiState(
    val name: String = "",
    val bio: String = "",
    val isUpdateProfile: Boolean = false,
)