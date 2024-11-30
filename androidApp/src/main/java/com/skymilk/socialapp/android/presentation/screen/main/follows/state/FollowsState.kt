package com.skymilk.socialapp.android.presentation.screen.main.follows.state

import com.skymilk.socialapp.android.presentation.common.dummy.FollowsUser

sealed interface FollowsState {

    data object Initial : FollowsState // 기본 초기 상태

    data object Loading : FollowsState // 로딩 상태

    data class Success(val followsUsers: List<FollowsUser>) : FollowsState // 데이터 로드 성공

    data class Error(val message: String) : FollowsState // 오류
}