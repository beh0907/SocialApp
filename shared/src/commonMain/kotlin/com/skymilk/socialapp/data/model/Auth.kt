package com.skymilk.socialapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
)

@Serializable
data class SignInRequest(
    val email: String,
    val password: String
)

@Serializable
data class AuthResponse(
    val data: AuthResponseData? = null,
    val errorMessage: String? = null
)

@Serializable
data class AuthResponseData(
    val id: Int,
    val name: String,
    val email: String,
    val bio: String,
    val avatar: String? = null,
    val token: String,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
)