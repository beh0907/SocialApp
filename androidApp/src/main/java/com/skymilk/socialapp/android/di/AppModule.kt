package com.skymilk.socialapp.android.di

import com.skymilk.socialapp.android.MainViewModel
import com.skymilk.socialapp.android.presentation.screen.auth.signIn.SignInViewModel
import com.skymilk.socialapp.android.presentation.screen.auth.signUp.SignUpViewModel
import com.skymilk.socialapp.android.presentation.screen.main.follows.FollowsViewModel
import com.skymilk.socialapp.android.presentation.screen.main.home.HomeViewModel
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.PostDetailViewModel
import com.skymilk.socialapp.android.presentation.screen.main.profile.ProfileViewModel
import com.skymilk.socialapp.android.presentation.screen.main.profileEdit.ProfileEditViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SignInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { (postId: Long) -> PostDetailViewModel(get(), get(), postId) }
    viewModel { (userId: Long) -> ProfileViewModel(get(), get(), get(), userId) }
    viewModel { (userId: Long) -> ProfileEditViewModel(get(), userId) }
    viewModel { (userId: Long, followsType: Int) -> FollowsViewModel(get(), userId, followsType) }
}

