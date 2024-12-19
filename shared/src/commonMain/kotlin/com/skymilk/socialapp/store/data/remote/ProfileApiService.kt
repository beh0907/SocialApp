package com.skymilk.socialapp.store.data.remote

import com.skymilk.socialapp.store.data.model.ProfileResponse
import com.skymilk.socialapp.util.Constants
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod

internal class ProfileApiService : KtorApi() {

    suspend fun getProfile(
        token: String,
        profileId: Long,
        currentUserId: Long
    ): ProfileResponse {
        val response = client.get {
            endPoint(path = "/profile/$profileId")
            parameter(key = Constants.CURRENT_USER_ID_PARAMETER, value = currentUserId)
            setToken(token = token)
        }

        return ProfileResponse(code = response.status, data = response.body())
    }

    suspend fun updateProfile(
        token: String,
        profileData: String,
        imageBytes: ByteArray?
    ): ProfileResponse {
        val response = client.submitFormWithBinaryData(
            formData = formData {
                append(key = "profile_data", value = profileData)
                imageBytes?.let {
                    append(
                        key = "profile_image",
                        value = it,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, value = "image/*")
                            append(HttpHeaders.ContentDisposition, value = "filename=profile.jpg")
                        }
                    )
                }
            },
        ) {
            endPoint("profile/update")
            setToken(token = token)
            setupMultipartRequest()
            method = HttpMethod.Post
        }

        return ProfileResponse(code = response.status, data = response.body())
    }
}