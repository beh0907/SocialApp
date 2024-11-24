package com.skymilk.socialapp.android.store.presentation.screen.auth.signIn

import com.skymilk.socialapp.android.store.presentation.screen.auth.signUp.SignUpEvent

sealed interface SignInEvent {

    data class UpdateEmail(val email: String) : SignInEvent

    data class UpdatePassword(val password: String) : SignInEvent

    data object SignIn : SignInEvent
}