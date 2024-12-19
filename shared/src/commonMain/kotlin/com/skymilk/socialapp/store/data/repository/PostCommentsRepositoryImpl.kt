package com.skymilk.socialapp.store.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.cash.paging.PagingData
import com.skymilk.socialapp.store.data.local.UserPreferences
import com.skymilk.socialapp.store.data.model.AddPostCommentParams
import com.skymilk.socialapp.store.data.model.RemovePostCommentParams
import com.skymilk.socialapp.store.data.paging.PostCommentsPagingSource
import com.skymilk.socialapp.store.data.remote.PostCommentsApiService
import com.skymilk.socialapp.util.Constants
import com.skymilk.socialapp.util.DispatcherProvider
import com.skymilk.socialapp.store.data.util.Result
import com.skymilk.socialapp.store.data.util.safeApiRequest
import com.skymilk.socialapp.store.domain.model.PostComment
import com.skymilk.socialapp.store.domain.repository.PostCommentsRepository
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

            if (response.code == HttpStatusCode.OK && response.data.comment != null) {
                Result.Success(data = response.data.comment.toPostComment(isOwner = userData.id == response.data.comment.userId))
            } else {
                Result.Error(message = "${response.data.message}")
            }
        }
    }

    override suspend fun removePostComment(
        postId: Long,
        commentId: Long
    ): Result<Boolean> {
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
                Result.Success(data = response.data.success)
            } else {
                Result.Error(message = "${response.data.message}")
            }
        }
    }

}