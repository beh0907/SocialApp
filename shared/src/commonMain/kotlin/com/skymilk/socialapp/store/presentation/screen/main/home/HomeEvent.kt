package com.skymilk.socialapp.store.presentation.screen.main.home

import com.skymilk.socialapp.store.domain.model.FollowsUser
import com.skymilk.socialapp.store.domain.model.Post

sealed interface HomeEvent {

    data object DismissOnBoarding : HomeEvent

    data object RetryData : HomeEvent

    data class FollowUser(val user: FollowsUser) : HomeEvent

    data class LikePost(val post: Post) : HomeEvent

}