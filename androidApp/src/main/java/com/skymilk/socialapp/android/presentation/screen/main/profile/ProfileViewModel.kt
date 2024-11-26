package com.skymilk.socialapp.android.presentation.screen.main.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.android.presentation.common.dummy.sampleComments
import com.skymilk.socialapp.android.presentation.common.dummy.samplePosts
import com.skymilk.socialapp.android.presentation.common.dummy.sampleProfiles
import com.skymilk.socialapp.android.presentation.screen.main.profile.state.UserInfoUiState
import com.skymilk.socialapp.android.presentation.screen.main.profile.state.UserPostsUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userId: Int
): ViewModel() {

    var userInfoUiState by mutableStateOf(UserInfoUiState())
        private set

    var userPostsUiState by mutableStateOf(UserPostsUiState())
        private set

    init {
        loadData()
    }

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.RetryData -> {
                loadData()
            }
            else -> {}
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            userInfoUiState = userInfoUiState.copy(isLoading = true)

            delay(500)

            userInfoUiState = userInfoUiState.copy(
                isLoading = false,
                profile = sampleProfiles.find { it.id == userId }
            )
        }

        viewModelScope.launch {
            userPostsUiState = userPostsUiState.copy(isLoading = true)

            delay(500)

            userPostsUiState = userPostsUiState.copy(
                isLoading = false,
                posts = samplePosts,
            )
        }
    }

}