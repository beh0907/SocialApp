package com.skymilk.socialapp.data.remote

import com.skymilk.socialapp.data.model.AuthResponse
import com.skymilk.socialapp.data.model.SignInParams
import com.skymilk.socialapp.data.model.SignUpParams
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal class AuthApiService: KtorApi() {

    suspend fun signUp(params: SignUpParams): AuthResponse = client.post {
        endPoint(path = "signUp")
        setBody(params)
    }.body()

    suspend fun signIn(params: SignInParams): AuthResponse = client.post {
        endPoint(path = "signIn")
        setBody(params)
    }.body()
}