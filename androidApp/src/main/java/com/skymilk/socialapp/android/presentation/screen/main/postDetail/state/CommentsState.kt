package com.skymilk.socialapp.android.presentation.screen.main.postDetail.state

import com.skymilk.socialapp.android.presentation.common.dummy.SampleComment

sealed interface CommentsState {

    data object Initial : CommentsState // 기본 초기 상태

    data object Loading : CommentsState // 로딩 상태

    data class Success(val comments: List<SampleComment>) : CommentsState // 데이터 로드 성공

    data class Error(val message: String) : CommentsState // 오류
}