package com.skymilk.socialapp.data.mapper

import com.skymilk.socialapp.data.model.AuthResponseData
import com.skymilk.socialapp.data.model.UserSettings
import com.skymilk.socialapp.domain.model.AuthResultData
import com.skymilk.socialapp.domain.model.Profile

internal fun AuthResponseData.toAuthResultData(): AuthResultData {
    return AuthResultData(
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

internal fun UserSettings.toDomainProfile(): Profile {
    return Profile(
        id = id,
        name = name,
        bio = bio,
        imageUrl = imageUrl,
        followersCount = followersCount,
        followingCount = followingCount,
        isOwnProfile = false,
        isFollowing = true
    )
}

internal fun Profile.toUserSettings(token: String): UserSettings {
    return UserSettings(
        id = id,
        name = name,
        bio = bio,
        imageUrl = imageUrl,
        followersCount = followersCount,
        followingCount = followingCount,
        token = token
    )
}