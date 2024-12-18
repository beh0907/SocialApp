package com.skymilk.socialapp.presentation.screen.main.home.state

import com.skymilk.socialapp.domain.model.FollowsUser

sealed interface OnBoardingState {

    data object Initial : OnBoardingState // 기본 초기 상태

    data object Loading : OnBoardingState // 로딩 상태

    data class Success(val users: List<FollowsUser>, val shouldShowOnBoarding: Boolean) : OnBoardingState // 데이터 로드 성공

    data object Dismiss : OnBoardingState // 창 닫기

    data class Error(val message: String) : OnBoardingState // 오류
}