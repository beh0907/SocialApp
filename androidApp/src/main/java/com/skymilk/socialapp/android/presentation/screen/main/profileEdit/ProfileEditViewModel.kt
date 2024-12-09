package com.skymilk.socialapp.android.presentation.screen.main.profileEdit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.android.presentation.common.dummy.sampleProfiles
import com.skymilk.socialapp.android.presentation.screen.main.profile.state.ProfileState
import com.skymilk.socialapp.android.presentation.screen.main.profileEdit.state.ProfileEditUiState
import com.skymilk.socialapp.android.presentation.util.MessageEvent
import com.skymilk.socialapp.android.presentation.util.sendEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.update

class ProfileEditViewModel(
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
        viewModelScope.launch {
            _profileState.update { ProfileState.Loading }

            delay(500)

            _profileState.update {
                val profile = sampleProfiles.find { it.id == userId }

                if (profile != null) {
                    uiState = ProfileEditUiState(bio = profile.bio, name = profile.name)

                    ProfileState.Success(profile = profile)
                }
                else ProfileState.Error("프로필을 찾을 수 없습니다.")
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