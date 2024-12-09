package com.skymilk.socialapp.data.remote

import com.skymilk.socialapp.data.model.ProfileResponse
import com.skymilk.socialapp.util.Constants
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class ProfileApiService : KtorApi() {

    suspend fun getProfile(
        token: String,
        profileId: Long,
        currentUserId: Long
    ): ProfileResponse {
        val response = client.get {
            endPoint(path = "profile/$profileId")
            parameter(key = Constants.USER_ID_PARAMETER, value = currentUserId)
            setToken(token = token)
        }

        return ProfileResponse(code = response.status, data = response.body())
    }
}