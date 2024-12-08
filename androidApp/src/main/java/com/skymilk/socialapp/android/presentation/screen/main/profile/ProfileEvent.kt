package com.skymilk.socialapp.android.presentation.screen.main.profile

import com.skymilk.socialapp.android.presentation.screen.main.home.HomeEvent
import com.skymilk.socialapp.domain.model.Post

sealed interface ProfileEvent {

    data object RetryData : ProfileEvent

    data class LikePost(val post: Post) : ProfileEvent

}