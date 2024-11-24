package com.skymilk.socialapp.android.store.presentation.screen.auth.signIn

data class SignInUIState(
    var email:String = "",
    var password:String = "",
    var isAuthenticating:Boolean = false,
    var authErrorMessage:String? = null,
    var authenticationSuccess:Boolean = false
)