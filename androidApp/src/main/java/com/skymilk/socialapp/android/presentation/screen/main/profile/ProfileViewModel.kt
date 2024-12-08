package com.skymilk.socialapp.android.presentation.screen.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.android.presentation.common.dummy.samplePosts
import com.skymilk.socialapp.android.presentation.common.dummy.sampleProfiles
import com.skymilk.socialapp.android.presentation.common.state.PostsState
import com.skymilk.socialapp.android.presentation.screen.main.profile.state.ProfileState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userId: Long
): ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Initial)
    val profileState = _profileState.asStateFlow()

    private val _postsState = MutableStateFlow<PostsState>(PostsState.Initial)
    val postsState = _postsState.asStateFlow()

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
            _profileState.update { ProfileState.Loading }

            delay(500)

            _profileState.update {
                val profile = sampleProfiles.find { it.id == userId }

                if (profile != null) ProfileState.Success(profile = profile)
                else ProfileState.Error("프로필을 찾을 수 없습니다.")
            }
        }

        viewModelScope.launch {
            _postsState.update { PostsState.Loading }

            delay(500)

            _postsState.update {
                PostsState.Success(posts = samplePosts)
            }
        }
    }

}