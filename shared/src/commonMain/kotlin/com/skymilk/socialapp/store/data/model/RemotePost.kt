package com.skymilk.socialapp.store.data.model

import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.data.util.DateFormatter
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
data class RemotePost(
    val postId: Long,
    val caption: String,
    val imageUrl: String,
    val createdAt: String,
    val likesCount: Int,
    val commentsCount: Int,
    val userId: Long,
    val userName: String,
    val userImageUrl: String?,
    val isLiked: Boolean = false,
    val isOwnPost: Boolean = false,
) {
    fun toPost(): Post {
        return Post(
            postId = postId,
            caption = caption,
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
internal data class PostResponse(
    val code: HttpStatusCode,
    val data: PostResponseData
)

@Serializable
internal data class PostResponseData(
    val success: Boolean,
    val post: RemotePost? = null,
    val message: String? = null
)


//게시글 작성
@Serializable
internal data class CreatePostParams(
    val caption: String,
    val userId: Long
)

//게시글 수정
@Serializable
internal data class UpdatePostParams(
    val caption: String,
    val imageUrl: String,
    val userId: Long,
    val postId: Long,
)