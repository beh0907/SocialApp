package com.skymilk.socialapp.store.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.skymilk.socialapp.store.data.local.UserPreferences
import com.skymilk.socialapp.store.data.model.CreatePostParams
import com.skymilk.socialapp.store.data.model.PostLikesParams
import com.skymilk.socialapp.store.data.model.UpdatePostParams
import com.skymilk.socialapp.store.data.paging.FeedPagingSource
import com.skymilk.socialapp.store.data.paging.UserPostsPagingSource
import com.skymilk.socialapp.store.data.remote.PostApiService
import com.skymilk.socialapp.store.data.util.Result
import com.skymilk.socialapp.store.data.util.safeApiRequest
import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.domain.repository.PostRepository
import com.skymilk.socialapp.util.Constants
import com.skymilk.socialapp.util.Constants.BASE_URL
import com.skymilk.socialapp.util.Constants.POST_IMAGES_FOLDER
import com.skymilk.socialapp.util.DispatcherProvider
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

    override suspend fun createPost(caption: String, images: List<ByteArray>): Result<Post> {
        return safeApiRequest(dispatcher) {
            val currentUserData = userPreferences.getUserData()

            val postData = Json.encodeToString(
                serializer = CreatePostParams.serializer(),
                value = CreatePostParams(caption = caption, userId = currentUserData.id)
            )

            val response = postApiService.createPost(
                token = currentUserData.token,
                postData = postData,
                images = images
            )

            if (response.code == HttpStatusCode.OK) {
                Result.Success(data = response.data.post!!.toPost())
            } else {
                Result.Error(message = response.data.message ?: Constants.UNEXPECTED_ERROR)
            }
        }
    }

    override suspend fun updatePost(
        post: Post,
        addImages: List<ByteArray>
    ): Result<Post> {
        return safeApiRequest(dispatcher) {
            val userData = userPreferences.getUserData()

            val postData = Json.encodeToString(
                serializer = UpdatePostParams.serializer(),
                value = UpdatePostParams(
                    caption = post.caption,
                    fileNames = post.imageUrls.map { it.substringAfterLast('/') },//파일명만 추출한다
                    userId = userData.id,
                    postId = post.postId
                )
            )

            //게시글 정보 수정 요청
            val response = postApiService.updatePost(
                token = userData.token,
                postData = postData,
                addImages = addImages
            )


            if (response.code == HttpStatusCode.OK) {
                var imageUrls = post.imageUrls

                //업로드한 이미지가 있다면 경로 요청
                if (addImages.isNotEmpty()) {
                    val updatedPostResponse = postApiService.getPost(
                        token = userData.token,
                        postId = post.postId,
                        currentUserId = userData.id
                    )

                    //업로드 이미지 경로 설정
                    updatedPostResponse.data.post?.let {
                        imageUrls = it.fileNames.map { name -> BASE_URL + POST_IMAGES_FOLDER + name }
                    }
                }

                //갱신된 게시물 객체 저장
                val updatedPost = post.copy(imageUrls = imageUrls)

                //최종 갱신 정보 리턴
                Result.Success(data = updatedPost)
            } else {
                Result.Error(message = response.data.message ?: Constants.UNEXPECTED_ERROR)
            }
        }
    }

    override suspend fun removePost(postId: Long): Result<Boolean> {
        return safeApiRequest(dispatcher) {
            val currentUserData = userPreferences.getUserData()

            val response = postApiService.removePost(
                token = currentUserData.token,
                postId = postId
            )

            if (response.code == HttpStatusCode.OK) {
                Result.Success(true)
            } else {
                Result.Error(message = response.data.message ?: Constants.UNEXPECTED_ERROR)
            }
        }
    }
}