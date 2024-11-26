package com.skymilk.socialapp.android.presentation.screen.main.profile.state

import com.skymilk.socialapp.android.presentation.common.dummy.Profile

data class UserInfoUiState(
    val isLoading: Boolean = false,
    val profile: Profile? = null,
    val errorMessages: String? = null,
)