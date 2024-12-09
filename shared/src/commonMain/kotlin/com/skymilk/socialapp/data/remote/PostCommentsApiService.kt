package com.skymilk.socialapp.data.remote

import com.skymilk.socialapp.data.model.AddPostCommentParams
import com.skymilk.socialapp.data.model.GetPostCommentsResponse
import com.skymilk.socialapp.data.model.PostCommentResponse
import com.skymilk.socialapp.data.model.RemovePostCommentParams
import com.skymilk.socialapp.util.Constants
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal class PostCommentsApiService : KtorApi() {
    //게시글 댓글 목록 가져오기
    suspend fun getPostComments(
        token: String,
        postId: Long,
        page: Int,
        pageSize: Int
    ): GetPostCommentsResponse {
        val httpResponse = client.get {
            endPoint(path = "/post/comments/$postId")
            parameter(key = Constants.PAGE_QUERY_PARAMETER, value = page)
            parameter(key = Constants.PAGE_SIZE_QUERY_PARAMETER, value = pageSize)
            setToken(token = token)
        }

        return GetPostCommentsResponse(code = httpResponse.status, data = httpResponse.body())
    }

    //게시글 댓글 작성
    suspend fun addPostComment(
        token: String,
        addPostCommentParams: AddPostCommentParams
    ): PostCommentResponse {
        val httpResponse = client.post {
            endPoint(path = "/post/comments/create")
            setBody(body = addPostCommentParams)
            setToken(token = token)
        }

        return PostCommentResponse(code = httpResponse.status, data = httpResponse.body())
    }

    //게시글 댓글 제거
    suspend fun removePostComment(
        token: String,
        removePostCommentParams: RemovePostCommentParams
    ): PostCommentResponse {
        val httpResponse = client.delete() {
            endPoint(path = "/post/comments/delete")
            setBody(body = removePostCommentParams)
            setToken(token = token)
        }

        return PostCommentResponse(code = httpResponse.status, data = httpResponse.body())
    }
}