package com.skymilk.socialapp.di

import com.skymilk.socialapp.MainViewModel
import com.skymilk.socialapp.store.presentation.screen.auth.signIn.SignInViewModel
import com.skymilk.socialapp.store.presentation.screen.auth.signUp.SignUpViewModel
import com.skymilk.socialapp.store.presentation.screen.main.follows.FollowsViewModel
import com.skymilk.socialapp.store.presentation.screen.main.home.HomeViewModel
import com.skymilk.socialapp.store.presentation.screen.main.postCreate.PostCreateViewModel
import com.skymilk.socialapp.store.presentation.screen.main.postDetail.PostDetailViewModel
import com.skymilk.socialapp.store.presentation.screen.main.profile.ProfileViewModel
import com.skymilk.socialapp.store.presentation.screen.main.profileEdit.ProfileEditViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::PostCreateViewModel)
    viewModelOf(::PostDetailViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::ProfileEditViewModel)
    viewModelOf(::FollowsViewModel)
}

