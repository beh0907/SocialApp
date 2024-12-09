package com.skymilk.socialapp.data.model

import com.skymilk.socialapp.domain.model.PostComment
import com.skymilk.socialapp.util.DateFormatter
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable


@Serializable
data class RemotePostComment(
    val commentId: Long,
    val content: String,
    val postId: Long,
    val userId: Long,
    val userName: String,
    val userImageUrl: String?,
    val createAt: String,
) {
    fun toPostComment(): PostComment {
        return PostComment(
            commentId = commentId,
            content = content,
            postId = postId,
            userId = userId,
            userName = userName,
            userImageUrl = userImageUrl,
            createAt = DateFormatter.parseDate(createAt),
        )
    }
}


internal data class GetPostCommentsResponse(
    val code: HttpStatusCode,
    val data: GetPostCommentsResponseData,
)

@Serializable
internal data class GetPostCommentsResponseData(
    val success: Boolean,
    val comments: List<RemotePostComment> = emptyList(),
    val message: String? = null
)

internal data class PostCommentResponse(
    val code: HttpStatusCode,
    val data: PostCommentResponseData,
)

@Serializable
internal data class PostCommentResponseData(
    val success: Boolean,
    val comment: RemotePostComment,
    val message: String? = null
)

@Serializable
internal data class AddPostCommentParams(
    val content:String,
    val postId: Long,
    val userId: Long,
)

@Serializable
internal data class RemovePostCommentParams(
    val postId: Long,
    val commentId: Long,
    val userId: Long,
)