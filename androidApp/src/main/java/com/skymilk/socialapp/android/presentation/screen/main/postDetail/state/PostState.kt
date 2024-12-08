package com.skymilk.socialapp.android.presentation.screen.main.postDetail.state

import com.skymilk.socialapp.domain.model.Post

sealed interface PostState {

    data object Initial : PostState // 기본 초기 상태

    data object Loading : PostState // 로딩 상태

    data class Success(val post: Post) : PostState // 데이터 로드 성공

    data class Error(val message: String) : PostState // 오류
}