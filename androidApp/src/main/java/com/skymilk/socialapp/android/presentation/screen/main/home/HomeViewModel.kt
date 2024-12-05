package com.skymilk.socialapp.android.presentation.screen.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.android.presentation.common.dummy.FollowsUser
import com.skymilk.socialapp.android.presentation.common.dummy.sampleFollowsUser
import com.skymilk.socialapp.android.presentation.common.dummy.samplePosts
import com.skymilk.socialapp.android.presentation.common.state.PostsState
import com.skymilk.socialapp.android.presentation.screen.main.home.state.OnBoardingState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {

    private val _onBoardingState = MutableStateFlow<OnBoardingState>(OnBoardingState.Initial)
    val onBoardingUiState = _onBoardingState.asStateFlow()

    private val _postsState = MutableStateFlow<PostsState>(PostsState.Initial)
    val postsUiState = _postsState.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.DismissOnBoarding -> dismissOnBoarding()

            is HomeEvent.RetryData -> loadData()

            is HomeEvent.FollowUser -> followUser(event.user, event.isFollow)
        }
    }

    private fun loadData() {
        _onBoardingState.update { OnBoardingState.Loading }
        _postsState.update { PostsState.Loading }

        //추천 유저 목록 불러오기
        viewModelScope.launch {
            delay(1000)

            _onBoardingState.update {
                OnBoardingState.Success(
                    users = sampleFollowsUser,
                    shouldShowOnBoarding = true
                )
            }
        }

        //게시글 목록 불러오기
        viewModelScope.launch {
            delay(1000)

            _postsState.update {
                PostsState.Success(posts = samplePosts)
            }
        }
    }

    private fun dismissOnBoarding() {
        //데이터가 로드 된 Success 상태일때 숨김 처리한다
        _onBoardingState.update {
            (it as? OnBoardingState.Success)?.copy(shouldShowOnBoarding = false) ?: it
        }
    }

    private fun followUser(user: FollowsUser, isFollow: Boolean) {

    }
}