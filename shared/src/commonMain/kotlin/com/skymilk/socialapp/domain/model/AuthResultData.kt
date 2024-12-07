package com.skymilk.socialapp.domain.model

data class AuthResultData(
    val id: Long,
    val name: String,
    val email: String,
    val bio: String,
    val imageUrl: String? = null,
    val token: String,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
)

