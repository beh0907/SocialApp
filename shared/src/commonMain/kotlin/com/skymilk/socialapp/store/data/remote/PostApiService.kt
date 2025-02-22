package com.skymilk.socialapp.store.data.remote

import com.skymilk.socialapp.store.data.model.PostResponse
import com.skymilk.socialapp.store.data.model.PostLikesParams
import com.skymilk.socialapp.store.data.model.PostLikesResponse
import com.skymilk.socialapp.store.data.model.PostsResponse
import com.skymilk.socialapp.util.Constants
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod

internal class PostApiService : KtorApi() {
    //게시글 목록
    suspend fun getFeedPosts(
        token: String,
        currentUserId: Long,
        page: Int,
        pageSize: Int
    ): PostsResponse {
        val httpResponse = client.get {
            endPoint(path = "/posts/feed")
            parameter(key = Constants.CURRENT_USER_ID_PARAMETER, value = currentUserId)
            parameter(key = Constants.PAGE_QUERY_PARAMETER, value = page)
            parameter(key = Constants.PAGE_SIZE_QUERY_PARAMETER, value = pageSize)
            setToken(token = token)
        }

        return PostsResponse(code = httpResponse.status, data = httpResponse.body())
    }

    //좋아요
    suspend fun likePost(
        token: String,
        params: PostLikesParams
    ): PostLikesResponse {
        val httpResponse = client.post {
            endPoint(path = "/post/likes/add")
            setBody(params)
            setToken(token = token)
        }
        return PostLikesResponse(code = httpResponse.status, data = httpResponse.body())
    }

    //좋아요 해제
    suspend fun dislikePost(
        token: String,
        params: PostLikesParams
    ): PostLikesResponse {
        val httpResponse = client.delete {
            endPoint(path = "/post/likes/remove")
            setBody(params)
            setToken(token = token)
        }
        return PostLikesResponse(code = httpResponse.status, data = httpResponse.body())
    }

    //유저 게시글 목록
    suspend fun getUserPosts(
        token: String,
        userId: Long,
        currentUserId: Long,
        page: Int,
        pageSize: Int
    ): PostsResponse {
        val httpResponse = client.get {
            endPoint(path = "/posts/$userId")
            parameter(key = Constants.CURRENT_USER_ID_PARAMETER, value = currentUserId)
            parameter(key = Constants.PAGE_QUERY_PARAMETER, value = page)
            parameter(key = Constants.PAGE_SIZE_QUERY_PARAMETER, value = pageSize)
            setToken(token = token)
        }
        return PostsResponse(code = httpResponse.status, data = httpResponse.body())
    }

    //선택한 게시글 상세정보
    suspend fun getPost(
        token: String,
        postId: Long,
        currentUserId: Long
    ): PostResponse {
        val httpResponse = client.get {
            endPoint(path = "/post/$postId")
            parameter(key = Constants.CURRENT_USER_ID_PARAMETER, value = currentUserId)
            setToken(token = token)
        }
        return PostResponse(code = httpResponse.status, data = httpResponse.body())
    }

    //게시글 생성
    suspend fun createPost(
        token: String,
        postData: String,
        images: List<ByteArray>
    ): PostResponse {
        val httpResponse = client.submitFormWithBinaryData(
            formData = formData {
                append(key = "post_data", value = postData)

                images.forEachIndexed { index, bytes ->
                    append(
                        key = "post_image_$index",
                        value = bytes,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, value = "image/*")
                            append(HttpHeaders.ContentDisposition, value = "filename=post_$index.jpg")
                        }
                    )
                }

            }
        ) {
            endPoint(path = "/post/create")
            setToken(token = token)
            setupMultipartRequest()
            method = HttpMethod.Post
        }
        return PostResponse(code = httpResponse.status, data = httpResponse.body())
    }

    //게시글 수정
    suspend fun updatePost(
        token: String,
        postData: String,
        addImages: List<ByteArray>
    ): PostResponse {
        val httpResponse = client.submitFormWithBinaryData(
            formData = formData {
                append(key = "post_data", value = postData)

                //추가할 이미지가 있다면 전달
                addImages.forEachIndexed { index, bytes ->
                    append(
                        key = "post_image_$index",
                        value = bytes,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, value = "image/*")
                            append(HttpHeaders.ContentDisposition, value = "filename=post_$index.jpg")
                        }
                    )
                }
            }
        ) {
            endPoint(path = "/post/update")
            setToken(token = token)
            setupMultipartRequest()
            method = HttpMethod.Post
        }
        return PostResponse(code = httpResponse.status, data = httpResponse.body())
    }

    //게시글 삭제
    suspend fun removePost(
        token: String,
        postId: Long,
    ): PostResponse {
        val httpResponse = client.delete {
            endPoint(path = "/post/$postId")
            setToken(token = token)
        }
        return PostResponse(code = httpResponse.status, data = httpResponse.body())
    }
}