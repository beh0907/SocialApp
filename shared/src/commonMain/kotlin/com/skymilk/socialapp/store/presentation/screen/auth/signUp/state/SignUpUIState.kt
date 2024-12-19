package com.skymilk.socialapp.store.presentation.screen.auth.signUp.state

data class SignUpUIState(
    var name:String = "",
    var email:String = "",
    var password:String = "",
    var passwordConfirm:String = "",
)