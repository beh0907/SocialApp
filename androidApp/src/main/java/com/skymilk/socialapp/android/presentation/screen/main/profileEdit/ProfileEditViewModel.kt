package com.skymilk.socialapp.android.presentation.screen.main.profileEdit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.android.presentation.screen.main.profile.state.ProfileState
import com.skymilk.socialapp.android.presentation.screen.main.profileEdit.state.ProfileEditUiState
import com.skymilk.socialapp.android.presentation.util.MessageEvent
import com.skymilk.socialapp.android.presentation.util.sendEvent
import com.skymilk.socialapp.domain.usecase.profile.ProfileUseCase
import com.skymilk.socialapp.data.util.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileEditViewModel(
    private val profileUseCase: ProfileUseCase,
    private val userId: Long
) : ViewModel() {
    var uiState by mutableStateOf(ProfileEditUiState())
        private set

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Initial)
    val profileState = _profileState.asStateFlow()

    init {
        loadProfile()
    }

    fun onEvent(event: ProfileEditEvent) {
        when (event) {
            is ProfileEditEvent.RetryData -> loadProfile()

            is ProfileEditEvent.UpdateProfile -> updateProfile()

            is ProfileEditEvent.UpdateBio -> uiState = uiState.copy(bio = event.bio)

            is ProfileEditEvent.UpdateName -> uiState = uiState.copy(name = event.name)
        }
    }

    private fun loadProfile() {
        _profileState.update { ProfileState.Loading }

        viewModelScope.launch {
            profileUseCase.getProfile(userId).collect { result ->
                when (result) {
                    is Result.Success -> {
                        val profile = result.data

                        //UI 수정
                        uiState = ProfileEditUiState(bio = profile.bio, name = profile.name)

                        //유저 정보 반영
                        _profileState.update { ProfileState.Success(profile = profile) }
                    }

                    is Result.Error -> {
                        ProfileState.Error("프로필을 찾을 수 없습니다.")
                    }
                }
            }
        }
    }

    private fun updateProfile() {
        viewModelScope.launch {
            uiState = uiState.copy(isUpdateProfile = true)

            delay(1000)

            sendEvent(MessageEvent.Toast("프로필이 갱신되었습니다."))

            uiState = uiState.copy(isUpdateProfile = false)
        }
    }

}