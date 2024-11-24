package com.skymilk.socialapp.data.remote

import com.skymilk.socialapp.data.model.AuthResponse
import com.skymilk.socialapp.data.model.SignInRequest
import com.skymilk.socialapp.data.model.SignUpRequest
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal class AuthService: KtorApi() {

    suspend fun signUp(request: SignUpRequest): AuthResponse = client.post {
        endPoint(path = "signUp")
        setBody(request)
    }.body()

    suspend fun signIn(request: SignInRequest): AuthResponse = client.post {
        endPoint(path = "signIn")
        setBody(request)
    }.body()
}