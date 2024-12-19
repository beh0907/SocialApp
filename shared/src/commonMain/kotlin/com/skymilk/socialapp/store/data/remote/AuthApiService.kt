package com.skymilk.socialapp.store.data.remote

import com.skymilk.socialapp.store.data.model.AuthResponse
import com.skymilk.socialapp.store.data.model.SignInParams
import com.skymilk.socialapp.store.data.model.SignUpParams
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal class AuthApiService: KtorApi() {

    suspend fun signUp(params: SignUpParams): AuthResponse  {
        val response = client.post {
            endPoint(path = "auth/signUp")
            setBody(params)
        }

        return response.body()
    }

    suspend fun signIn(params: SignInParams): AuthResponse {
        val response = client.post {
            endPoint(path = "auth/signIn")
            setBody(params)
        }

        return response.body()
    }
}