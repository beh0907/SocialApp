package com.skymilk.socialapp.domain.model

data class Post(
    val id: Long,
    val text: String,
    val imageUrl: String,
    val createdAt: String,
    val likesCount: Int,
    val commentsCount: Int,
    val authorId: Long,
    val authorName: String,
    val authorImage: String,
    val isLiked: Boolean = false,
    val isOwnPost: Boolean = false,
)