package com.skymilk.socialapp.domain.usecase.profile

import com.skymilk.socialapp.domain.model.Profile
import com.skymilk.socialapp.domain.repository.ProfileRepository
import com.skymilk.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class GetProfile(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(profileId: Long): Flow<Result<Profile>> {
        return profileRepository.getProfile(profileId)
    }
}