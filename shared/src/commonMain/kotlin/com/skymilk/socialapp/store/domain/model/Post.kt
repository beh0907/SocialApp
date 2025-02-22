package com.skymilk.socialapp.store.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val postId: Long,
    val caption: String,
    val imageUrls: List<String>,
    val createdAt: String,
    val likesCount: Int,
    val commentsCount: Int,
    val userId: Long,
    val userName: String,
    val userImageUrl: String?,
    val isLiked: Boolean = false,
    val isOwnPost: Boolean = false,
) {
    constructor() : this(0L, "", listOf(), "", 0, 0, 0L, "", null, false, false)
}