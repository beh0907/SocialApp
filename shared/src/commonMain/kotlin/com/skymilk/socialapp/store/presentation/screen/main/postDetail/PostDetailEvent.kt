package com.skymilk.socialapp.store.presentation.screen.main.postDetail

import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.domain.model.PostComment

sealed interface PostDetailEvent {

    data class SetSelectedPostOption(val isSelected: Boolean) : PostDetailEvent

    data class SetShowRemoveDialog(val isShow: Boolean) : PostDetailEvent

    data class SetSelectedPostComment(val postComment: PostComment?) : PostDetailEvent

    data class LikePost(val post: Post) : PostDetailEvent

    data class RemovePost(val post: Post) : PostDetailEvent

    data class ChangeComment(val comment: String) : PostDetailEvent

    data class RemoveComment(val comment: PostComment) : PostDetailEvent

    data object SendComment : PostDetailEvent

}