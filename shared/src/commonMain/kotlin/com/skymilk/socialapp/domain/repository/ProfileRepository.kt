package com.skymilk.socialapp.domain.repository

import com.skymilk.socialapp.domain.model.Profile
import com.skymilk.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(profileId: Long): Flow<Result<Profile>>
}