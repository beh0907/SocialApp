package com.skymilk.socialapp.store.data.repository

import com.skymilk.socialapp.store.data.local.UserPreferences
import com.skymilk.socialapp.store.data.mapper.toAuthResultData
import com.skymilk.socialapp.store.data.model.SignInParams
import com.skymilk.socialapp.store.data.model.SignUpParams
import com.skymilk.socialapp.store.data.model.toUserSettings
import com.skymilk.socialapp.store.data.remote.AuthApiService
import com.skymilk.socialapp.util.DispatcherProvider
import com.skymilk.socialapp.store.data.util.Result
import com.skymilk.socialapp.store.data.util.safeApiRequest
import com.skymilk.socialapp.store.domain.model.AuthResultData
import com.skymilk.socialapp.store.domain.repository.AuthRepository

internal class AuthRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val authApiService: AuthApiService,
    private val userPreferences: UserPreferences
) : AuthRepository {
    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Result<AuthResultData> {
        return safeApiRequest(dispatcher) {
            val request = SignUpParams(name, email, password)
            val authResponse = authApiService.signUp(request)

            if (authResponse.data == null) {
                Result.Error(message = authResponse.message)
            } else {
                val authResultData = authResponse.data.toAuthResultData()

                //dataStore 저장
                userPreferences.setUserData(authResultData.toUserSettings())

                //결과 리턴
                Result.Success(data = authResultData)
            }
        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<AuthResultData> {
        return safeApiRequest(dispatcher) {
            val request = SignInParams(email, password)
            val authResponse = authApiService.signIn(request)

            if (authResponse.data == null) {
                Result.Error(message = authResponse.message)
            } else {
                val authResultData = authResponse.data.toAuthResultData()

                //dataStore 저장
                userPreferences.setUserData(authResultData.toUserSettings())

                //결과 리턴
                Result.Success(data = authResultData)
            }
        }
    }
}