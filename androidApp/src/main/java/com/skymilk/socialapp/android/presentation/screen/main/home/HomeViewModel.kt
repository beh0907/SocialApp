package com.skymilk.socialapp.android.presentation.screen.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.android.presentation.common.dummy.FollowsUser
import com.skymilk.socialapp.android.presentation.common.dummy.sampleFollowsUser
import com.skymilk.socialapp.android.presentation.common.dummy.samplePosts
import com.skymilk.socialapp.android.presentation.screen.main.home.state.OnBoardingState
import com.skymilk.socialapp.android.presentation.common.state.PostsState
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

        viewModelScope.launch {
            delay(1000)

            _onBoardingState.update {
                OnBoardingState.Success(
                    users = sampleFollowsUser,
                    shouldShowOnBoarding = true
                )
            }
        }

        viewModelScope.launch {
            delay(1000)

            _postsState.update {
                PostsState.Success(posts = samplePosts)
            }
        }
    }

    private fun dismissOnBoarding() {
        println("dismissOnBoarding")

        if (_onBoardingState is OnBoardingState.Success) {
            _onBoardingState.update {
                _onBoardingState.copy(shouldShowOnBoarding = false)
            }


        }
    }

    private fun followUser(user: FollowsUser, isFollow: Boolean) {

    }
}