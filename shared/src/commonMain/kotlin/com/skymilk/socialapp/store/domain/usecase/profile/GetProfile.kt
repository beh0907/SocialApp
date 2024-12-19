package com.skymilk.socialapp.store.domain.usecase.profile

import com.skymilk.socialapp.store.domain.model.Profile
import com.skymilk.socialapp.store.domain.repository.ProfileRepository
import com.skymilk.socialapp.store.data.util.Result
import kotlinx.coroutines.flow.Flow

class GetProfile(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(profileId: Long): Flow<Result<Profile>> {
        return profileRepository.getProfile(profileId)
    }
}