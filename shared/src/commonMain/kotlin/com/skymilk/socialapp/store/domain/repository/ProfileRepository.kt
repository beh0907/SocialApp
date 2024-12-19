package com.skymilk.socialapp.store.domain.repository

import com.skymilk.socialapp.store.domain.model.Profile
import com.skymilk.socialapp.store.data.util.Result
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(profileId: Long): Flow<Result<Profile>>

    suspend fun updateProfile(profile: Profile, imageBytes: ByteArray? = null): Result<Profile>
}