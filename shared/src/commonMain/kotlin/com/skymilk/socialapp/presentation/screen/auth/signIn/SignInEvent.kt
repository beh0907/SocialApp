package com.skymilk.socialapp.presentation.screen.auth.signIn

sealed interface SignInEvent {

    data class UpdateEmail(val email: String) : SignInEvent

    data class UpdatePassword(val password: String) : SignInEvent

    data object SignIn : SignInEvent
}