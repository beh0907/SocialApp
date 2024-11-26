package com.skymilk.socialapp.android.presentation.common.datastore

import com.skymilk.socialapp.domain.model.AuthResultData
import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val id: Int = -1,
    val name: String = "",
    val email: String = "",
    val bio: String = "",
    val avatar: String? = null,
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
        avatar = avatar,
        token = token,
        followersCount = followersCount,
        followingCount = followingCount
    )
}

fun UserSettings.toAuthResultData(): AuthResultData {
    return AuthResultData(
        id = id,
        name = name,
        email = email,
        bio = bio,
        avatar = avatar,
        token = token,
        followersCount = followersCount,
        followingCount = followingCount
    )
}