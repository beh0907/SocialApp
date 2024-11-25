package com.skymilk.socialapp.android.presentation.screen.main.home.state

import com.skymilk.socialapp.android.presentation.common.dummy.FollowsUser

data class OnBoardingUiState(
    val isLoading:Boolean = false,
    val users:List<FollowsUser> = emptyList(),
    val errorMessage: String? = null,
    val shouldShowOnBoarding: Boolean = false
)