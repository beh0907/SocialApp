package com.skymilk.socialapp.store.data.remote

import com.skymilk.socialapp.store.data.model.FollowOrUnFollowResponse
import com.skymilk.socialapp.store.data.model.FollowsParams
import com.skymilk.socialapp.store.data.model.FollowsResponse
import com.skymilk.socialapp.util.Constants
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal class FollowApiService : KtorApi() {

    //팔로우
    suspend fun followUser(token: String, params: FollowsParams): FollowOrUnFollowResponse {
        val response = client.post {
            endPoint(path = "follows/follow")
            setBody(params)
            setToken(token)
        }

        return FollowOrUnFollowResponse(code = response.status, data = response.body())
    }

    //언 팔로우
    suspend fun unFollowUser(token: String, params: FollowsParams): FollowOrUnFollowResponse {
        val response = client.post {
            endPoint(path = "follows/unfollow")
            setBody(params)
            setToken(token)
        }

        return FollowOrUnFollowResponse(code = response.status, data = response.body())
    }

    //팔로우 추천 유저 목록
    suspend fun getFollowableUsers(token: String, userId: Long): FollowsResponse {
        val response = client.get {
            endPoint(path = "follows/suggestions")
            parameter(key = Constants.USER_ID_PARAMETER, value = userId)
            setToken(token)
        }

        return FollowsResponse(code = response.status, data = response.body())
    }

    //나의 팔로우 목록
    suspend fun getMyFollows(
        token: String, userId: Long,
        page: Int,
        pageSize: Int,
        followsEndPoint: String // 1 = followers / 2 = following
    ): FollowsResponse {
        val response = client.get {
            endPoint("follows/$followsEndPoint")
            parameter(key = Constants.USER_ID_PARAMETER, value = userId)
            parameter(key = Constants.PAGE_QUERY_PARAMETER, value = page)
            parameter(key = Constants.PAGE_SIZE_QUERY_PARAMETER, value = pageSize)
            setToken(token)
        }

        return FollowsResponse(code = response.status, data = response.body())
    }
}