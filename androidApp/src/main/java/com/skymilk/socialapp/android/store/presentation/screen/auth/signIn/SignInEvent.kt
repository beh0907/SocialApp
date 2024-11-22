package com.skymilk.socialapp.android.store.presentation.screen.auth.signIn

sealed interface SignInEvent {

    data class UpdateEmail(val email: String) : SignInEvent

    data class UpdatePassword(val password: String) : SignInEvent

}