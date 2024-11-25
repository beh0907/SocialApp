package com.skymilk.socialapp.domain.usecase.auth

data class AuthUseCase(
    val signUpUseCase: SignUpUseCase,
    val signInUseCase: SignInUseCase,
)
