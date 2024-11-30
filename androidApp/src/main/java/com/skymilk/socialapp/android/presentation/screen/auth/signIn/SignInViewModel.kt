package com.skymilk.socialapp.android.presentation.screen.auth.signIn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.android.presentation.common.datastore.UserSettings
import com.skymilk.socialapp.android.presentation.common.datastore.toUserSettings
import com.skymilk.socialapp.android.presentation.common.state.AuthState
import com.skymilk.socialapp.android.presentation.screen.auth.signIn.state.SignInUIState
import com.skymilk.socialapp.android.presentation.util.Event
import com.skymilk.socialapp.android.presentation.util.sendEvent
import com.skymilk.socialapp.domain.usecase.auth.SignInUseCase
import com.skymilk.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val dataStore: DataStore<UserSettings>
) : ViewModel() {
    var uiState by mutableStateOf(SignInUIState())
        private set

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState = _authState

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.UpdateEmail -> updateEmail(event.email)

            is SignInEvent.UpdatePassword -> updatePassword(event.password)

            is SignInEvent.SignIn -> signIn()
        }
    }

    private fun updateEmail(username: String) {
        uiState = uiState.copy(email = username)
    }

    private fun updatePassword(username: String) {
        uiState = uiState.copy(password = username)
    }

    private fun signIn() {
        viewModelScope.launch {
            authState.update { AuthState.Loading }

            val authResultData = signInUseCase(
                email = uiState.email,
                password = uiState.password
            )

            authState.update {
                when (authResultData) {
                    is Result.Error -> {
                        val message = authResultData.message.toString()
                        sendEvent(Event.Toast(message = message))

                        AuthState.Error(message)
                    }

                    is Result.Success -> {
                        //datastore 유저정보 저장
                        dataStore.updateData { authResultData.data!!.toUserSettings() }

                        AuthState.Authenticated
                    }
                }
            }
        }
    }
}