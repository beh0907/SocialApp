package com.skymilk.socialapp.di

import com.skymilk.socialapp.store.data.remote.AuthApiService
import com.skymilk.socialapp.store.data.remote.FollowApiService
import com.skymilk.socialapp.store.data.remote.PostApiService
import com.skymilk.socialapp.store.data.remote.PostCommentsApiService
import com.skymilk.socialapp.store.data.remote.ProfileApiService
import com.skymilk.socialapp.store.data.repository.AuthRepositoryImpl
import com.skymilk.socialapp.store.data.repository.FollowsRepositoryImpl
import com.skymilk.socialapp.store.data.repository.PostCommentsRepositoryImpl
import com.skymilk.socialapp.store.data.repository.PostRepositoryImpl
import com.skymilk.socialapp.store.data.repository.ProfileRepositoryImpl
import com.skymilk.socialapp.store.domain.repository.AuthRepository
import com.skymilk.socialapp.store.domain.repository.FollowsRepository
import com.skymilk.socialapp.store.domain.repository.PostCommentsRepository
import com.skymilk.socialapp.store.domain.repository.PostRepository
import com.skymilk.socialapp.store.domain.repository.ProfileRepository
import com.skymilk.socialapp.store.domain.usecase.auth.AuthUseCase
import com.skymilk.socialapp.store.domain.usecase.auth.SignIn
import com.skymilk.socialapp.store.domain.usecase.auth.SignUp
import com.skymilk.socialapp.store.domain.usecase.follows.FollowOrUnFollow
import com.skymilk.socialapp.store.domain.usecase.follows.FollowsUseCase
import com.skymilk.socialapp.store.domain.usecase.follows.GetFollowableUsers
import com.skymilk.socialapp.store.domain.usecase.follows.GetMyFollows
import com.skymilk.socialapp.store.domain.usecase.post.CreatePost
import com.skymilk.socialapp.store.domain.usecase.post.GetFeedPosts
import com.skymilk.socialapp.store.domain.usecase.post.GetPost
import com.skymilk.socialapp.store.domain.usecase.post.GetUserPosts
import com.skymilk.socialapp.store.domain.usecase.post.LikeOrDislikePost
import com.skymilk.socialapp.store.domain.usecase.post.PostUseCase
import com.skymilk.socialapp.store.domain.usecase.postComments.AddPostComment
import com.skymilk.socialapp.store.domain.usecase.postComments.GetPostComments
import com.skymilk.socialapp.store.domain.usecase.postComments.PostCommentsUseCase
import com.skymilk.socialapp.store.domain.usecase.postComments.RemovePostComment
import com.skymilk.socialapp.store.domain.usecase.profile.GetProfile
import com.skymilk.socialapp.store.domain.usecase.profile.ProfileUseCase
import com.skymilk.socialapp.store.domain.usecase.profile.UpdateProfile
import com.skymilk.socialapp.util.provideDispatcher
import org.koin.dsl.module

//인증
private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    factory { AuthApiService() }
    factory {
        AuthUseCase(
            SignUp(get()),
            SignIn(get())
        )
    }
}

//팔로우
private val followModule = module {
    single<FollowsRepository> { FollowsRepositoryImpl(get(), get(), get()) }
    factory { FollowApiService() }
    factory {
        FollowsUseCase(
            GetMyFollows(get()),
            GetFollowableUsers(get()),
            FollowOrUnFollow(get()),
        )
    }
}

//게시물
private val postModule = module {
    single<PostRepository> { PostRepositoryImpl(get(), get(), get()) }
    factory { PostApiService() }
    factory {
        PostUseCase(
            GetPost(get()),
            GetFeedPosts(get()),
            GetUserPosts(get()),
            LikeOrDislikePost(get()),
            CreatePost(get())
        )
    }
}

//게시물 댓글
private val postCommentsModule = module {
    single<PostCommentsRepository> { PostCommentsRepositoryImpl(get(), get(), get()) }
    factory { PostCommentsApiService() }
    factory {
        PostCommentsUseCase(
            GetPostComments(get()),
            AddPostComment(get()),
            RemovePostComment(get())
        )
    }
}

//프로필
private val profileModule = module {
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get(), get()) }
    factory { ProfileApiService() }
    factory {
        ProfileUseCase(
            GetProfile(get()),
            UpdateProfile(get()),
        )
    }
}

private val utilModule = module {
    factory { provideDispatcher() }
}

fun getSharedModule() =
    listOf(
        authModule,
        followModule,
        postModule,
        postCommentsModule,
        profileModule,
        utilModule,
        platformModule
    )