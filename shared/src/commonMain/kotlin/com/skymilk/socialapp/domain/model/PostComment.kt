package com.skymilk.socialapp.domain.model

data class PostComment(
    val commentId: Long,
    val content: String,
    val postId: Long,
    val userId: Long,
    val userName: String,
    val userImageUrl: String?,
    val createAt: String,
    val isOwner: Boolean = false
)