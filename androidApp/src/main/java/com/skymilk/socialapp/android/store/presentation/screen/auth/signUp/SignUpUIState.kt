package com.skymilk.socialapp.android.store.presentation.screen.auth.signUp

data class SignUpUIState(
    var username:String = "",
    var email:String = "",
    var password:String = "",
    var passwordConfirm:String = "",
)