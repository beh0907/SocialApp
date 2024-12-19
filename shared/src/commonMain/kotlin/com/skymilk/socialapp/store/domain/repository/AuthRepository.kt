package com.skymilk.socialapp.store.domain.repository

import com.skymilk.socialapp.store.domain.model.AuthResultData
import com.skymilk.socialapp.store.data.util.Result

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