package com.skymilk.socialapp.android.store.presentation.screen.auth.signUp

data class SignUpUIState(
    var name:String = "",
    var email:String = "",
    var password:String = "",
    var passwordConfirm:String = "",
    var isAuthenticating:Boolean = false,
    var authErrorMessage:String? = null,
    var authenticationSuccess:Boolean = false
)