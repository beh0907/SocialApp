package com.skymilk.socialapp.android.di

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.skymilk.socialapp.android.MainViewModel
import com.skymilk.socialapp.android.presentation.common.datastore.UserSettingsSerializer
import com.skymilk.socialapp.android.presentation.screen.auth.signIn.SignInViewModel
import com.skymilk.socialapp.android.presentation.screen.auth.signUp.SignUpViewModel
import com.skymilk.socialapp.android.presentation.screen.main.home.HomeViewModel
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.PostDetailViewModel
import com.skymilk.socialapp.android.presentation.screen.main.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SignInViewModel(get(), get()) }
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { (postId: String) -> PostDetailViewModel(postId) }
    viewModel { (userId: Int) -> ProfileViewModel(userId) }

    single {
        DataStoreFactory.create(
            serializer = UserSettingsSerializer,
            produceFile = { get<Context>().dataStoreFile("user_settings") }
        )
    }
}

