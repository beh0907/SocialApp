package com.skymilk.socialapp.android.presentation.screen.main.home

import com.skymilk.socialapp.android.presentation.common.dummy.FollowsUser

sealed interface HomeEvent {

    data object DismissOnBoarding : HomeEvent

    data object RetryData : HomeEvent

    data class FollowUser(val user: FollowsUser, val isFollow: Boolean) : HomeEvent

}