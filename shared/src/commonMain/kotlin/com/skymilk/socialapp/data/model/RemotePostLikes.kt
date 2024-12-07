package com.skymilk.socialapp.data.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
data class PostLikesParams(
    val postId: Long,
    val userId: Long,
)

//게시글 좋아요 응답
internal data class PostLikesResponse(
    val code: HttpStatusCode,
    val data: PostLikesResponseData,
)

@Serializable
internal data class PostLikesResponseData(
    val success: Boolean,
    val message: String? = null
)