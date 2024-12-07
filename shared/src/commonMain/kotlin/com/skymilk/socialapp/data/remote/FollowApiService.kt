package com.skymilk.socialapp.data.remote

import com.skymilk.socialapp.data.model.FollowOrUnFollowResponse
import com.skymilk.socialapp.data.model.FollowsParams
import com.skymilk.socialapp.data.model.FollowsResponse
import com.skymilk.socialapp.util.Constants
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal class FollowApiService : KtorApi() {

    //팔로우
    suspend fun followUser(userToken: String, params: FollowsParams): FollowOrUnFollowResponse {
        val response = client.post {
            endPoint(path = "follows/follow")
            setBody(params)
            setToken(userToken)
        }

        return FollowOrUnFollowResponse(code = response.status, data = response.body())
    }

    //팔로우
    suspend fun unFollowUser(userToken: String, params: FollowsParams): FollowOrUnFollowResponse {
        val response = client.post {
            endPoint(path = "follows/unfollow")
            setBody(params)
            setToken(userToken)
        }

        return FollowOrUnFollowResponse(code = response.status, data = response.body())
    }

    //팔로우
    suspend fun getFollowableUsers(userToken: String, userId: Long): FollowsResponse {
        val response = client.get {
            endPoint(path = "follows/suggestions")
            parameter(key = Constants.USER_ID_PARAMETER, value = userId)
            setToken(userToken)
        }

        return FollowsResponse(code = response.status, data = response.body())
    }
}