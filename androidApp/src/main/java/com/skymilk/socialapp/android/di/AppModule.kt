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
    viewModel { HomeViewModel() }
    viewModel { (postId: String) -> PostDetailViewModel(postId) }
    viewModel { (userId: Int) -> ProfileViewModel(userId) }
    viewModel { (userId: Int) -> ProfileEditViewModel(userId) }
    viewModel { (userId: Int, followsType: Int) -> FollowsViewModel(userId, followsType) }
}

