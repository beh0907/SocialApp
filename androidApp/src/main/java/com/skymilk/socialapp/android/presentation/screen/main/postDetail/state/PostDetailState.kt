package com.skymilk.socialapp.android.presentation.screen.main.postDetail.state

import com.skymilk.socialapp.domain.model.Post

sealed interface PostDetailState {

    data object Initial : PostDetailState // 기본 초기 상태

    data object Loading : PostDetailState // 로딩 상태

    data class Success(val post: Post) : PostDetailState // 데이터 로드 성공

    data class Error(val message: String) : PostDetailState // 오류
}