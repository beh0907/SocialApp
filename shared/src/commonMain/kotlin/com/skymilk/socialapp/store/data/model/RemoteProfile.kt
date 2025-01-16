package com.skymilk.socialapp.store.data.model

import com.skymilk.socialapp.store.domain.model.Profile
import com.skymilk.socialapp.util.Constants.BASE_URL
import com.skymilk.socialapp.util.Constants.PROFILE_IMAGES_FOLDER
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
internal data class RemoteProfile(
    val userId: Long,
    val name: String,
    val bio: String,
    val fileName: String? = null,
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
            imageUrl = BASE_URL + PROFILE_IMAGES_FOLDER + fileName,
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
    val fileName: String? = null
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