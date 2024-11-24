package com.skymilk.socialapp.di

import com.skymilk.socialapp.data.remote.AuthService
import com.skymilk.socialapp.data.repository.AuthRepositoryImpl
import com.skymilk.socialapp.domain.repository.AuthRepository
import com.skymilk.socialapp.domain.usecase.auth.SignInUseCase
import com.skymilk.socialapp.domain.usecase.auth.SignUpUseCase
import com.skymilk.socialapp.util.provideDispatcher
import org.koin.dsl.module

private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    factory { AuthService() }
    factory { SignUpUseCase() }
    factory { SignInUseCase() }
}

private val utilModule = module {
    factory{ provideDispatcher() }
}

fun getSharedModule() = listOf(authModule, utilModule)