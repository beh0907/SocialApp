package com.skymilk.socialapp.domain.repository

import com.skymilk.socialapp.domain.model.Profile
import com.skymilk.socialapp.data.util.Result
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(profileId: Long): Flow<Result<Profile>>

    suspend fun updateProfile(profile: Profile, imageBytes: ByteArray? = null): Result<Profile>
}