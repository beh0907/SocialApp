package com.skymilk.socialapp.presentation.screen.main.postDetail

import com.skymilk.socialapp.domain.model.Post
import com.skymilk.socialapp.domain.model.PostComment

sealed interface PostDetailEvent {

    data object RetryData : PostDetailEvent

    data class LikePost(val post: Post) : PostDetailEvent

    data class ChangeComment(val comment: String) : PostDetailEvent

    data class RemoveComment(val comment: PostComment) : PostDetailEvent

    data object SendComment : PostDetailEvent

}