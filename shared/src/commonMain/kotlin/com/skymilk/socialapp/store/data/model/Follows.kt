package com.skymilk.socialapp.store.data.model

import com.skymilk.socialapp.store.domain.model.FollowsUser
import com.skymilk.socialapp.util.Constants.BASE_URL
import com.skymilk.socialapp.util.Constants.PROFILE_IMAGES_FOLDER
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
internal data class FollowsParams(
    val followerId: Long,
    val followingId: Long,
)

@Serializable
internal data class RemoteFollowsUser(
    val id: Long = -1,
    val name: String = "",
    val bio: String = "",
    val fileName: String? = null,
    val isFollowing: Boolean = false,
) {
    fun toDomainFollowUser() =
        FollowsUser(id, name, bio, BASE_URL + PROFILE_IMAGES_FOLDER + fileName, isFollowing)
}

//팔로워 유저 목록 응답
internal data class FollowsResponse(
    val code: HttpStatusCode,
    val data: FollowsResponseData,
)

@Serializable
internal data class FollowsResponseData(
    val success: Boolean,
    val follows: List<RemoteFollowsUser> = emptyList(),
    val message: String? = null
)

//팔로우/언팔로우 응답
internal data class FollowOrUnFollowResponse(
    val code: HttpStatusCode,
    val data: FollowOrUnFollowResponseData,
)

@Serializable
internal data class FollowOrUnFollowResponseData(
    val success: Boolean,
    val message: String? = null,
)