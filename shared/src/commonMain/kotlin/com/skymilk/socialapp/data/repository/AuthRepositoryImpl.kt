package com.skymilk.socialapp.data.repository

import com.skymilk.socialapp.data.local.UserPreferences
import com.skymilk.socialapp.data.mapper.toAuthResultData
import com.skymilk.socialapp.data.model.SignInRequest
import com.skymilk.socialapp.data.model.SignUpRequest
import com.skymilk.socialapp.data.model.toUserSettings
import com.skymilk.socialapp.data.remote.AuthService
import com.skymilk.socialapp.domain.model.AuthResultData
import com.skymilk.socialapp.domain.repository.AuthRepository
import com.skymilk.socialapp.util.DispatcherProvider
import com.skymilk.socialapp.util.Result
import kotlinx.coroutines.withContext

internal class AuthRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val authService: AuthService,
    private val userPreferences: UserPreferences
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
                    val authResultData = authResponse.data.toAuthResultData()

                    //dataStore 저장
                    userPreferences.setUserData(authResultData.toUserSettings())

                    //결과 리턴
                    Result.Success(data = authResultData)
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
                val request = SignInRequest(email,password)
                val authResponse = authService.signIn(request)

                if (authResponse.data == null) {
                    Result.Error(message = authResponse.errorMessage)
                } else {
                    val authResultData = authResponse.data.toAuthResultData()

                    //dataStore 저장
                    userPreferences.setUserData(authResultData.toUserSettings())

                    //결과 리턴
                    Result.Success(data = authResultData)
                }
            } catch (e: Exception) {
                Result.Error(message = "서버와 통신할 수 없습니다. 다시 시도 해주세요.")
            }
        }
    }
}