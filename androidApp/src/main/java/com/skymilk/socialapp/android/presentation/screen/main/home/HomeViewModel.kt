package com.skymilk.socialapp.android.presentation.screen.main.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.android.presentation.common.dummy.FollowsUser
import com.skymilk.socialapp.android.presentation.common.dummy.sampleFollowsUser
import com.skymilk.socialapp.android.presentation.common.dummy.samplePosts
import com.skymilk.socialapp.android.presentation.screen.main.home.state.OnBoardingUiState
import com.skymilk.socialapp.android.presentation.screen.main.home.state.PostsUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {

    var onBoardingUiState by mutableStateOf(OnBoardingUiState())
        private set

    var postsUiState by mutableStateOf(PostsUiState())
        private set

    init {
        loadData()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.DismissOnBoarding -> onBoardingUiState.copy(shouldShowOnBoarding = false)

            is HomeEvent.RetryData -> loadData()

            is HomeEvent.FollowUser -> followUser(event.user, event.isFollow)
        }
    }

    private fun loadData() {
        onBoardingUiState = onBoardingUiState.copy(isLoading = true)
        postsUiState = postsUiState.copy(isLoading = true)

        viewModelScope.launch {
            delay(1000)

            onBoardingUiState = onBoardingUiState.copy(
                isLoading = false,
                users = sampleFollowsUser,
                shouldShowOnBoarding = true
            )
        }

        viewModelScope.launch {
            delay(1000)

            postsUiState = postsUiState.copy(
                isLoading = false,
                posts = samplePosts
            )
        }
    }

    private fun followUser(user: FollowsUser, isFollow: Boolean) {

    }
}