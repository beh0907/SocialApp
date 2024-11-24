package com.skymilk.socialapp.android.di

import com.skymilk.socialapp.android.store.presentation.screen.auth.signIn.SignInViewModel
import com.skymilk.socialapp.android.store.presentation.screen.auth.signUp.SignUpViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SignInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
}