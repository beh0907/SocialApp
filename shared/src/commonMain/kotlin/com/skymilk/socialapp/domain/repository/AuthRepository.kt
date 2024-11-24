package com.skymilk.socialapp.domain.repository

import com.skymilk.socialapp.domain.model.AuthResultData
import com.skymilk.socialapp.util.Result

interface AuthRepository {
    suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Result<AuthResultData>

    suspend fun signIn(
        email: String,
        password: String
    ): Result<AuthResultData>
}