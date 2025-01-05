package com.skymilk.socialapp.store.presentation.navigation.routes

import com.skymilk.socialapp.store.domain.model.Post
import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    //로그인/회원가입 화면
    //////////////////////////////////////////////////////
    @Serializable
    data object AuthGraph : Routes

    @Serializable
    data object SignInScreen : Routes // 로그인 화면

    @Serializable
    data object SignUpScreen : Routes // 회원가입 화면
    //////////////////////////////////////////////////////

    //메인 화면
    //////////////////////////////////////////////////////
    @Serializable
    data object MainGraph : Routes

    //하단 바 메뉴
    @Serializable
    data object HomeScreen : Routes // 메인 화면

    @Serializable
    data class FollowersScreen(val userId: Long) : Routes // 팔로워 목록 화면

    @Serializable
    data class FollowingScreen(val userId: Long) : Routes // 팔로윙 목록 화면

    @Serializable
    data object PostCreateScreen : Routes // 게시글 생성 화면

    @Serializable
    data class PostEditScreen(val post: Post) : Routes // 게시글 수정 화면

    @Serializable
    data class PostDetailScreen(val postId: Long) : Routes // 게시글 상세 화면

    @Serializable
    data class ProfileScreen(val userId: Long) : Routes // 프로필 화면

    @Serializable
    data class ProfileEditScreen(val userId: Long) : Routes // 프로필 수정 화면
    //////////////////////////////////////////////////////
}