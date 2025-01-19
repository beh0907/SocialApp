package com.skymilk.socialapp.store.data.model

import com.skymilk.socialapp.store.domain.model.AuthResultData
import com.skymilk.socialapp.util.Constants.BASE_URL
import com.skymilk.socialapp.util.Constants.PROFILE_IMAGES_FOLDER
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
    val fileName: String? = null,
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
        imageUrl = if (fileName == null) null else BASE_URL + PROFILE_IMAGES_FOLDER + fileName,
        token = token,
        followersCount = followersCount,
        followingCount = followingCount
    )
}