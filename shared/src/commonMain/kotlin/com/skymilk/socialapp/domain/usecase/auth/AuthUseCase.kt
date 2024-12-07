package com.skymilk.socialapp.domain.usecase.auth

data class AuthUseCase(
    val signUpUseCase: SignUp,
    val signIn: SignIn,
)
