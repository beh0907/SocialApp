package com.skymilk.socialapp.store.domain.usecase.auth

data class AuthUseCase(
    val signUpUseCase: SignUp,
    val signIn: SignIn,
)
