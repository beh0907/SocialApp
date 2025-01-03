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
import com.skymilk.socialapp.store.domain.usecase.post.RemovePost
import com.skymilk.socialapp.store.domain.usecase.post.UpdatePost
import com.skymilk.socialapp.store.domain.usecase.postComments.AddPostComment
import com.skymilk.socialapp.store.domain.usecase.postComments.GetPostComments
import com.skymilk.socialapp.store.domain.usecase.postComments.PostCommentsUseCase
import com.skymilk.socialapp.store.domain.usecase.postComments.RemovePostComment
import com.skymilk.socialapp.store.domain.usecase.profile.GetProfile
import com.skymilk.socialapp.store.domain.usecase.profile.ProfileUseCase
import com.skymilk.socialapp.store.domain.usecase.profile.UpdateProfile
import com.skymilk.socialapp.util.provideDispatcher
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

//인증
private val authModule = module {
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::AuthApiService)

    factory {
        AuthUseCase(
            SignUp(get()), SignIn(get())
        )
    }
}

//팔로우
private val followModule = module {
    singleOf(::FollowsRepositoryImpl) { bind<FollowsRepository>() }
    singleOf(::FollowApiService)
    factory {
        FollowsUseCase(
            GetMyFollows(get()),
            GetFollowableUsers(get()),
            FollowOrUnFollow(get()),
        )
    }
}

//게시글
private val postModule = module {
    singleOf(::PostRepositoryImpl) { bind<PostRepository>() }
    singleOf(::PostApiService)
    factory {
        PostUseCase(
            GetPost(get()),
            GetFeedPosts(get()),
            GetUserPosts(get()),
            LikeOrDislikePost(get()),
            CreatePost(get()),
            UpdatePost(get()),
            RemovePost(get())
        )
    }
}

//게시글 댓글
private val postCommentsModule = module {
    singleOf(::PostCommentsRepositoryImpl) { bind<PostCommentsRepository>() }
    singleOf(::PostCommentsApiService)
    factory {
        PostCommentsUseCase(
            GetPostComments(get()), AddPostComment(get()), RemovePostComment(get())
        )
    }
}

//프로필
private val profileModule = module {
    singleOf(::ProfileRepositoryImpl) { bind<ProfileRepository>() }
    singleOf(::ProfileApiService)
    factory {
        ProfileUseCase(
            GetProfile(get()),
            UpdateProfile(get()),
        )
    }
}

//유틸
private val utilModule = module {
    factoryOf(::provideDispatcher)
}


fun getSharedModule() = listOf(
    authModule,
    followModule,
    postModule,
    postCommentsModule,
    profileModule,
    utilModule,
    platformModule,
)