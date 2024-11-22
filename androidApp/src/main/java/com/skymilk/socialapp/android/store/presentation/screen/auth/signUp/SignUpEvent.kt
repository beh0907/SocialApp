package com.skymilk.socialapp.android.store.presentation.screen.auth.signUp

sealed interface SignUpEvent {

    data class UpdateUserName(val username: String) : SignUpEvent

    data class UpdateEmail(val email: String) : SignUpEvent

    data class UpdatePassword(val password: String) : SignUpEvent

    data class UpdatePasswordConfirm(val password: String) : SignUpEvent

}