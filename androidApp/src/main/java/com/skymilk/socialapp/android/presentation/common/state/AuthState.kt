package com.skymilk.socialapp.android.presentation.common.state

sealed interface AuthState {
    
    data object Initial : AuthState // 기본 초기 상태
    
    data object Loading : AuthState // 로딩 상태
    
    data object Authenticated : AuthState // 로그인 성공
    
    data object Unauthenticated : AuthState // 유저 정보 없을 때
    
    data class Error(val message: String) : AuthState // 로그인 실패
}