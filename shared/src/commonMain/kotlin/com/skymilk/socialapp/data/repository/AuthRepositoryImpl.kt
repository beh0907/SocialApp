package com.skymilk.socialapp.data.repository

import com.skymilk.socialapp.data.mapper.toAuthResultData
import com.skymilk.socialapp.data.model.SignInRequest
import com.skymilk.socialapp.data.model.SignUpRequest
import com.skymilk.socialapp.data.remote.AuthService
import com.skymilk.socialapp.domain.model.AuthResultData
import com.skymilk.socialapp.domain.repository.AuthRepository
import com.skymilk.socialapp.util.DispatcherProvider
import com.skymilk.socialapp.util.Result
import kotlinx.coroutines.withContext

internal class AuthRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val authService: AuthService
): AuthRepository {
    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Result<AuthResultData> {
        return withContext(dispatcher.io) {
            try {
                val request = SignUpRequest(name,email,password)
                val authResponse = authService.signUp(request)

                if (authResponse.data == null) {
                    Result.Error(message = authResponse.errorMessage)
                } else {
                    Result.Success(data = authResponse.data.toAuthResultData())
                }
            } catch (e: Exception) {
                Result.Error(message = "서버와 통신할 수 없습니다. 다시 시도 해주세요.")
            }
        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<AuthResultData> {
        return withContext(dispatcher.io) {
            try {
                println("1")

                val request = SignInRequest(email,password)
                println("2")
                val authResponse = authService.signIn(request)
                println("3")

                if (authResponse.data == null) {
                    println("4-1")
                    Result.Error(message = authResponse.errorMessage)
                } else {
                    println("4-2")
                    Result.Success(data = authResponse.data.toAuthResultData())
                }
            } catch (e: Exception) {
                println("5")
                Result.Error(message = "서버와 통신할 수 없습니다. 다시 시도 해주세요.")
            }
        }
    }
}