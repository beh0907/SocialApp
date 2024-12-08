package com.skymilk.socialapp.domain.model

data class Post(
    val postId: Long,
    val caption: String,
    val imageUrl: String,
    val createdAt: String,
    val likesCount: Int,
    val commentsCount: Int,
    val userId: Long,
    val userName: String,
    val userImageUrl: String?,
    val isLiked: Boolean = false,
    val isOwnPost: Boolean = false,
)