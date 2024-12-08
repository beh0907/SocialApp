package com.skymilk.socialapp.di

import com.skymilk.socialapp.data.remote.AuthApiService
import com.skymilk.socialapp.data.remote.FollowApiService
import com.skymilk.socialapp.data.remote.PostApiService
import com.skymilk.socialapp.data.repository.AuthRepositoryImpl
import com.skymilk.socialapp.data.repository.FollowsRepositoryImpl
import com.skymilk.socialapp.data.repository.PostRepositoryImpl
import com.skymilk.socialapp.domain.repository.AuthRepository
import com.skymilk.socialapp.domain.repository.FollowsRepository
import com.skymilk.socialapp.domain.repository.PostRepository
import com.skymilk.socialapp.domain.usecase.auth.AuthUseCase
import com.skymilk.socialapp.domain.usecase.auth.SignIn
import com.skymilk.socialapp.domain.usecase.auth.SignUp
import com.skymilk.socialapp.domain.usecase.follows.FollowOrUnFollow
import com.skymilk.socialapp.domain.usecase.follows.FollowsUseCase
import com.skymilk.socialapp.domain.usecase.follows.GetFollowableUsers
import com.skymilk.socialapp.domain.usecase.post.GetFeedPosts
import com.skymilk.socialapp.domain.usecase.post.GetUserPosts
import com.skymilk.socialapp.domain.usecase.post.LikeOrDislikePost
import com.skymilk.socialapp.domain.usecase.post.PostUseCase
import com.skymilk.socialapp.util.provideDispatcher
import org.koin.dsl.module

//인증
private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    single { AuthApiService() }
    single {
        AuthUseCase(
            SignUp(get()),
            SignIn(get())
        )
    }
}

//팔로우
private val followModule = module {
    single<FollowsRepository> { FollowsRepositoryImpl(get(), get(), get()) }
    single { FollowApiService() }
    single {
        FollowsUseCase(
            FollowOrUnFollow(get()),
            GetFollowableUsers(get())
        )
    }
}

//게시물
private val postModule = module {
    single<PostRepository> { PostRepositoryImpl(get(), get(), get()) }
    single { PostApiService() }
    single {
        PostUseCase(
            GetFeedPosts(get()),
            GetUserPosts(get()),
            LikeOrDislikePost(get())
        )
    }
}

private val utilModule = module {
    factory { provideDispatcher() }
}

fun getSharedModule() = listOf(authModule, followModule, postModule, utilModule, platformModule)