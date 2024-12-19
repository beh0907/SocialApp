package com.skymilk.socialapp.store.domain.usecase.profile

import com.skymilk.socialapp.store.data.util.Result
import com.skymilk.socialapp.store.domain.model.Profile
import com.skymilk.socialapp.store.domain.repository.ProfileRepository
import com.skymilk.socialapp.util.Constants

class UpdateProfile(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(profile: Profile, imageBytes: ByteArray?): Result<Profile> {
        if (profile.name.length !in 2..20) {
            return Result.Error(message = Constants.INVALID_INPUT_NAME_MESSAGE)
        }

        if (profile.bio.length > 150) {
            return Result.Error(message = Constants.INVALID_INPUT_BIO_MESSAGE)
        }

        return profileRepository.updateProfile(profile, imageBytes)
    }
}