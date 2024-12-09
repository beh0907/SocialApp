package com.skymilk.socialapp.android.presentation.screen.main.profile.state

import com.skymilk.socialapp.domain.model.Profile

sealed interface ProfileState {

    data object Initial : ProfileState // 기본 초기 상태

    data object Loading : ProfileState // 로딩 상태

    data class Success(val profile: Profile) : ProfileState // 데이터 로드 성공

    data class Error(val message: String) : ProfileState // 오류
}