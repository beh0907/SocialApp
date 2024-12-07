package com.skymilk.socialapp.domain.usecase.auth

import com.skymilk.socialapp.domain.model.AuthResultData
import com.skymilk.socialapp.domain.repository.AuthRepository
import com.skymilk.socialapp.util.Result

class SignUp(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String,
    ): Result<AuthResultData> {
        if (name.isBlank() || name.length < 2) {
            return Result.Error(message = "이름은 2자리 이상 입력해주세요.")
        }

        if (email.isBlank() || "@" !in email) {
            return Result.Error(message = "이메일 형식이 올바르지 않습니다.")
        }

        if (password.isBlank() || password.length < 8) {
            return Result.Error(message = "비밀번호는 8자리 이상으로 입력해주세요.")
        }

        if (password != passwordConfirm) {
            return Result.Error(message = "비밀번호가 일치하지 않습니다.")
        }

        return authRepository.signUp(name, email, password)
    }
}