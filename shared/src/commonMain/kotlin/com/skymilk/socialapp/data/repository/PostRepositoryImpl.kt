package com.skymilk.socialapp.data.repository

import app.cash.paging.PagingData
import com.skymilk.socialapp.data.local.UserPreferences
import com.skymilk.socialapp.data.model.CreatePostParams
import com.skymilk.socialapp.data.model.PostLikesParams
import com.skymilk.socialapp.data.paging.FeedPagingSource
import com.skymilk.socialapp.data.paging.UserPostsPagingSource
import com.skymilk.socialapp.data.remote.PostApiService
import com.skymilk.socialapp.util.Constants
import com.skymilk.socialapp.util.DispatcherProvider
import com.skymilk.socialapp.data.util.Result
import com.skymilk.socialapp.data.util.safeApiRequest
import com.skymilk.socialapp.domain.model.Post
import com.skymilk.socialapp.domain.repository.PostRepository
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

internal class PostRepositoryImpl(
    private val postApiService: PostApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : PostRepository {

    override fun getFeedPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.PAGE_SIZE),
            pagingSourceFactory = {
                FeedPagingSource(
                    postApiService = postApiService,
                    userPreferences = userPreferences
                )
            }
        )
            .flow
            .flowOn(dispatcher.io)
    }

    override fun getUserPosts(userId: Long): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.PAGE_SIZE),
            pagingSourceFactory = {
                UserPostsPagingSource(
                    postApiService = postApiService,
                    userPreferences = userPreferences,
                    userId = userId
                )
            }
        )
            .flow
            .flowOn(dispatcher.io)
    }

    override suspend fun likeOrDislikePost(postId: Long, shouldLike: Boolean): Result<Boolean> {
        return safeApiRequest(dispatcher) {
            val userData = userPreferences.getUserData()
            val likeParams = PostLikesParams(postId = postId, userId = userData.id)

            val response = if (shouldLike) {
                postApiService.likePost(userData.token, likeParams)
            } else {
                postApiService.dislikePost(userData.token, likeParams)
            }

            if (response.code == HttpStatusCode.OK) {
                Result.Success(data = response.data.success)
            } else {
                Result.Error(message = "${response.data.message}")
            }
        }
    }

    override suspend fun getPost(postId: Long): Result<Post> {
        return safeApiRequest(dispatcher) {
            val userData = userPreferences.getUserData()

            val response = postApiService.getPost(
                token = userData.token,
                currentUserId = userData.id,
                postId = postId
            )

            if (response.code == HttpStatusCode.OK) {
                Result.Success(data = response.data.post!!.toPost())
            } else {
                Result.Error(message = response.data.message!!)
            }
        }
    }

    override suspend fun createPost(text: String, imageBytes: ByteArray): Result<Post> {
        return safeApiRequest(dispatcher) {
            val currentUserData = userPreferences.getUserData()

            val postData = Json.encodeToString(
                serializer = CreatePostParams.serializer(),
                value = CreatePostParams(text = text, userId = currentUserData.id)
            )

            val response = postApiService.createPost(
                token = currentUserData.token,
                postData = postData,
                imageBytes = imageBytes
            )

            if (response.code == HttpStatusCode.OK) {
                Result.Success(data = response.data.post!!.toPost())
            } else {
                Result.Error(message = response.data.message ?: Constants.UNEXPECTED_ERROR)
            }
        }
    }
}