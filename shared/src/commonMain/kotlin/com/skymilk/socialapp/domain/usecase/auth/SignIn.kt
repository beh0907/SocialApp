package com.skymilk.socialapp.domain.usecase.auth

import com.skymilk.socialapp.domain.model.AuthResultData
import com.skymilk.socialapp.domain.repository.AuthRepository
import com.skymilk.socialapp.data.util.Result
import com.skymilk.socialapp.util.Constants

class SignIn(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String): Result<AuthResultData> {

        if (email.isBlank() || "@" !in email) {
            return Result.Error(message = Constants.INVALID_INPUT_EMAIL_MESSAGE)
        }

        if (password.isBlank() || password.length < 8) {
            return Result.Error(message = Constants.INVALID_INPUT_PASSWORD_MESSAGE)
        }

        return authRepository.signIn(email, password)
    }

}