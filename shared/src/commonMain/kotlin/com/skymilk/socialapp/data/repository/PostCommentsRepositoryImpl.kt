package com.skymilk.socialapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.cash.paging.PagingData
import com.skymilk.socialapp.data.local.UserPreferences
import com.skymilk.socialapp.data.model.AddPostCommentParams
import com.skymilk.socialapp.data.model.RemovePostCommentParams
import com.skymilk.socialapp.data.paging.PostCommentsPagingSource
import com.skymilk.socialapp.data.remote.PostCommentsApiService
import com.skymilk.socialapp.util.Constants
import com.skymilk.socialapp.util.DispatcherProvider
import com.skymilk.socialapp.data.util.Result
import com.skymilk.socialapp.data.util.safeApiRequest
import com.skymilk.socialapp.domain.model.PostComment
import com.skymilk.socialapp.domain.repository.PostCommentsRepository
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

internal class PostCommentsRepositoryImpl(
    private val postCommentsApiService: PostCommentsApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : PostCommentsRepository {
    override fun getPostComments(
        postId: Long,
    ): Flow<PagingData<PostComment>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.PAGE_SIZE),
            pagingSourceFactory = {
                PostCommentsPagingSource(
                    postCommentsApiService = postCommentsApiService,
                    userPreferences = userPreferences,
                    postId = postId
                )
            }
        )
            .flow
            .flowOn(dispatcher.io)
    }

    override suspend fun addPostComment(
        postId: Long,
        content: String
    ): Result<PostComment> {
        return safeApiRequest(dispatcher) {
            val userData = userPreferences.getUserData()
            val response = postCommentsApiService.addPostComment(
                token = userData.token,
                addPostCommentParams = AddPostCommentParams(
                    postId = postId,
                    userId = userData.id,
                    content = content
                )
            )

            if (response.code == HttpStatusCode.OK) {
                Result.Success(data = response.data.comment.toPostComment(isOwner = userData.id == response.data.comment.userId))
            } else {
                Result.Error(message = "${response.data.message}")
            }
        }
    }

    override suspend fun removePostComment(
        postId: Long,
        commentId: Long
    ): Result<PostComment> {
        return safeApiRequest(dispatcher) {
            val userData = userPreferences.getUserData()
            val response = postCommentsApiService.removePostComment(
                token = userData.token,
                removePostCommentParams = RemovePostCommentParams(
                    postId = postId,
                    commentId = commentId,
                    userId = userData.id,
                )
            )

            if (response.code == HttpStatusCode.OK) {
                Result.Success(data = response.data.comment.toPostComment(isOwner = userData.id == response.data.comment.userId))
            } else {
                Result.Error(message = "${response.data.message}")
            }
        }
    }

}