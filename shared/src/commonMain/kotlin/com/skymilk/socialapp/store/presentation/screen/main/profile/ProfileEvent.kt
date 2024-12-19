package com.skymilk.socialapp.store.presentation.screen.main.profile

import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.domain.model.Profile

sealed interface ProfileEvent {

    data object RetryData : ProfileEvent

    data class FollowUser(val profile: Profile) : ProfileEvent

    data class LikePost(val post: Post) : ProfileEvent

}