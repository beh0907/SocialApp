package com.skymilk.socialapp.data.repository

import com.skymilk.socialapp.data.local.UserPreferences
import com.skymilk.socialapp.data.model.CreatePostParams
import com.skymilk.socialapp.data.model.PostLikesParams
import com.skymilk.socialapp.data.model.PostsResponse
import com.skymilk.socialapp.data.model.UserSettings
import com.skymilk.socialapp.data.remote.PostApiService
import com.skymilk.socialapp.domain.model.Post
import com.skymilk.socialapp.domain.repository.PostRepository
import com.skymilk.socialapp.util.Constants
import com.skymilk.socialapp.util.DispatcherProvider
import com.skymilk.socialapp.util.Result
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import kotlinx.serialization.json.Json

internal class PostRepositoryImpl(
    private val postApiService: PostApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : PostRepository {
    override suspend fun getFeedPosts(page: Int, pageSize: Int): Result<List<Post>> {
        return fetchPosts(
            apiCall = { currentUserData ->
                postApiService.getFeedPosts(
                    userToken = currentUserData.token,
                    currentUserId = currentUserData.id,
                    page = page,
                    pageSize = pageSize
                )
            }
        )
    }

    override suspend fun likeOrDislikePost(postId: Long, shouldLike: Boolean): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val likeParams = PostLikesParams(postId = postId, userId = userData.id)

                val apiResponse = if (shouldLike) {
                    postApiService.likePost(userData.token, likeParams)
                } else {
                    postApiService.dislikePost(userData.token, likeParams)
                }

                if (apiResponse.code == HttpStatusCode.OK) {
                    Result.Success(data = apiResponse.data.success)
                } else {
                    Result.Error(data = false, message = "${apiResponse.data.message}")
                }
            } catch (ioException: IOException) {
                Result.Error(message = Constants.NO_INTERNET_ERROR_MESSAGE)
            } catch (exception: Throwable) {
                Result.Error(
                    message = "${exception.message}"
                )
            }
        }
    }

    override suspend fun getUserPosts(userId: Long, page: Int, pageSize: Int): Result<List<Post>> {
        return fetchPosts(
            apiCall = { currentUserData ->
                postApiService.getUserPosts(
                    token = currentUserData.token,
                    userId = userId,
                    currentUserId = currentUserData.id,
                    page = page,
                    pageSize = pageSize
                )
            }
        )
    }

    private suspend fun fetchPosts(
        apiCall: suspend (UserSettings) -> PostsResponse
    ): Result<List<Post>> {
        return withContext(dispatcher.io) {
            try {
                val currentUserData = userPreferences.getUserData()
                val apiResponse = apiCall(currentUserData)

                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(data = apiResponse.data.posts.map { it.toDomainPost() })
                    }

                    else -> {
                        Result.Error(message = Constants.UNEXPECTED_ERROR)
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = Constants.NO_INTERNET_ERROR_MESSAGE)
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.cause}")
            }
        }
    }

    override suspend fun getPost(postId: Long): Result<Post> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()

                val apiResponse = postApiService.getPost(
                    token = userData.token,
                    currentUserId = userData.id,
                    postId = postId
                )

                if (apiResponse.code == HttpStatusCode.OK) {
                    Result.Success(data = apiResponse.data.post!!.toDomainPost())
                } else {
                    Result.Error(message = apiResponse.data.message!!)
                }
            } catch (ioException: IOException) {
                Result.Error(message = Constants.NO_INTERNET_ERROR_MESSAGE)
            } catch (exception: Throwable) {
                Result.Error(message = Constants.UNEXPECTED_ERROR)
            }
        }
    }

    override suspend fun createPost(text: String, imageBytes: ByteArray): Result<Post> {
        return withContext(dispatcher.io) {
            val currentUserData = userPreferences.getUserData()

            val postData = Json.encodeToString(
                serializer = CreatePostParams.serializer(),
                value = CreatePostParams(text = text, userId = currentUserData.id)
            )

            val apiResponse = postApiService.createPost(
                token = currentUserData.token,
                newPostData = postData,
                imageBytes = imageBytes
            )

            if (apiResponse.code == HttpStatusCode.OK) {
                Result.Success(data = apiResponse.data.post!!.toDomainPost())
            } else {
                Result.Error(message = apiResponse.data.message ?: Constants.UNEXPECTED_ERROR)
            }
        }
    }
}