package com.skymilk.socialapp.data.model

import com.skymilk.socialapp.domain.model.Profile
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
internal data class RemoteProfile(
    val id: Long,
    val name: String,
    val bio: String,
    val imageUrl: String,
    val followersCount: Int,
    val followingCount: Int,
    val isOwnProfile: Boolean,
    val isFollowing: Boolean
) {
    fun toProfile(): Profile {
        return Profile(
            id = id,
            name = name,
            bio = bio,
            imageUrl = imageUrl,
            followersCount = followersCount,
            followingCount = followingCount,
            isOwnProfile = isOwnProfile,
            isFollowing = isFollowing
        )
    }
}

//프로필 정보 응답
internal data class ProfileResponse(
    val code: HttpStatusCode,
    val data: ProfileResponseData,
)

@Serializable
internal data class ProfileResponseData(
    val success: Boolean,
    val profile: RemoteProfile?,
    val message: String? = null
)