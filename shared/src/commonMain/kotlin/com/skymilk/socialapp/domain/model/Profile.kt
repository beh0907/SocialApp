package com.skymilk.socialapp.domain.model

data class Profile(
    val id: Long,
    val name: String,
    val bio: String,
    val imageUrl: String?,
    val followersCount: Int,
    val followingCount: Int,
    val isOwnProfile: Boolean,
    val isFollowing: Boolean
)