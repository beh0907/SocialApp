package com.skymilk.socialapp.data.mapper

import com.skymilk.socialapp.data.model.AuthResponseData
import com.skymilk.socialapp.domain.model.AuthResultData

internal fun AuthResponseData.toAuthResultData(): AuthResultData {
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