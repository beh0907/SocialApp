package com.skymilk.socialapp.android.presentation.screen.main.profileEdit.state

data class ProfileEditUiState(
    val name: String = "",
    val bio: String = "",
    val imageBytes: ByteArray? = null,
    val isUpdateProfile: Boolean = false,
)