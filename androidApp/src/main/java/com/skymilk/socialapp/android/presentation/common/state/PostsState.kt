package com.skymilk.socialapp.android.presentation.common.state

import com.skymilk.socialapp.domain.model.Post

sealed interface PostsState {

    data object Initial : PostsState // 기본 초기 상태

    data object Loading : PostsState // 로딩 상태

    data class Success(val posts: List<Post>) : PostsState // 데이터 로드 성공

    data class Error(val message: String) : PostsState // 오류
}