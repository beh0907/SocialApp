package com.skymilk.socialapp.data.model

import com.skymilk.socialapp.domain.model.Post
import com.skymilk.socialapp.util.DateFormatter
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable



//게시물 작성
@Serializable
internal data class CreatePostParams(
    val text: String,
    val userId: Long
)

@Serializable
data class RemotePost(
    val id: Long,
    val text: String,
    val imageUrl: String,
    val createdAt: String,
    val likesCount: Int,
    val commentsCount: Int,
    val userId: Long,
    val userName: String,
    val userImageUrl: String,
    val isLiked: Boolean = false,
    val isOwnPost: Boolean = false,
) {
    fun toDomainPost(): Post {
        return Post(
            postId = id,
            caption = text,
            imageUrl = imageUrl,
            createdAt = DateFormatter.parseDate(createdAt),
            likesCount = likesCount,
            commentsCount = commentsCount,
            userId = userId,
            userName = userName,
            userImageUrl = userImageUrl,
            isLiked = isLiked,
            isOwnPost = isOwnPost
        )
    }
}

//게시글 목록 응답
internal data class PostsResponse(
    val code: HttpStatusCode,
    val data: PostsResponseData,
)

@Serializable
internal data class PostsResponseData(
    val success: Boolean,
    val posts: List<RemotePost> = emptyList(),
    val message: String? = null
)

//게시글 응답
internal data class PostApiResponse(
    val code: HttpStatusCode,
    val data: PostApiResponseData
)

@Serializable
internal data class PostApiResponseData(
    val success: Boolean,
    val post: RemotePost? = null,
    val message: String? = null
)