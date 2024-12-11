package com.skymilk.socialapp.domain.usecase.auth

import com.skymilk.socialapp.domain.model.AuthResultData
import com.skymilk.socialapp.domain.repository.AuthRepository
import com.skymilk.socialapp.data.util.Result
import com.skymilk.socialapp.util.Constants

class SignUp(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String,
    ): Result<AuthResultData> {
        if (name.length !in 2..20) {
            return Result.Error(message = Constants.INVALID_INPUT_NAME_MESSAGE)
        }

        if (email.isBlank() || "@" !in email) {
            return Result.Error(message = Constants.INVALID_INPUT_EMAIL_MESSAGE)
        }

        if (password.isBlank() || password.length < 8) {
            return Result.Error(message = Constants.INVALID_INPUT_PASSWORD_MESSAGE)
        }

        if (password != passwordConfirm) {
            return Result.Error(message = Constants.INVALID_INPUT_PASSWORD_CONFIRM_MESSAGE)
        }

        return authRepository.signUp(name, email, password)
    }
}