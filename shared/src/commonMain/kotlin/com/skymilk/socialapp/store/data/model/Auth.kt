package com.skymilk.socialapp.store.data.model

import com.skymilk.socialapp.store.domain.model.AuthResultData
import kotlinx.serialization.Serializable

@Serializable
data class SignUpParams(
    val name: String,
    val email: String,
    val password: String
)

@Serializable
data class SignInParams(
    val email: String,
    val password: String
)

@Serializable
data class AuthResponse(
    val data: AuthResponseData? = null,
    val message: String? = null
)

@Serializable
data class AuthResponseData(
    val id: Long,
    val name: String,
    val email: String,
    val bio: String,
    val imageUrl: String? = null,
    val token: String,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
)

@Serializable
data class UserSettings(
    val id: Long = -1,
    val name: String = "",
    val email: String = "",
    val bio: String = "",
    val imageUrl: String? = null,
    val token: String = "",
    val followersCount: Int = 0,
    val followingCount: Int = 0,
)

fun AuthResultData.toUserSettings(): UserSettings {
    return UserSettings(
        id = id,
        name = name,
        email = email,
        bio = bio,
        imageUrl = imageUrl,
        token = token,
        followersCount = followersCount,
        followingCount = followingCount
    )
}