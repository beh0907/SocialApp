package com.skymilk.socialapp.data.model

import com.skymilk.socialapp.domain.model.Profile
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
internal data class RemoteProfile(
    val userId: Long,
    val name: String,
    val bio: String,
    val imageUrl: String? = null,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val isFollowing: Boolean = false,
    val isOwnProfile: Boolean = false,
) {
    fun toProfile(): Profile {
        return Profile(
            id = userId,
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

//프로필 정보 갱신 파라미터
@Serializable
data class UpdateProfileParams(
    val userId: Long,
    val name: String,
    val bio: String,
    val imageUrl: String? = null
)

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