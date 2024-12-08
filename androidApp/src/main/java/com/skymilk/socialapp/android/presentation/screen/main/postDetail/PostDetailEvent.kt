package com.skymilk.socialapp.android.presentation.screen.main.postDetail

import com.skymilk.socialapp.android.presentation.screen.main.home.HomeEvent
import com.skymilk.socialapp.domain.model.Post

sealed interface PostDetailEvent {

    data object RetryData : PostDetailEvent

    data class LikePost(val post: Post) : PostDetailEvent

}