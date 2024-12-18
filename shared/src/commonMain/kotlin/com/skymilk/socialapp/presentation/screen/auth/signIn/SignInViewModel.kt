package com.skymilk.socialapp.presentation.screen.auth.signIn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.data.util.Result
import com.skymilk.socialapp.domain.usecase.auth.AuthUseCase
import com.skymilk.socialapp.presentation.common.state.AuthState
import com.skymilk.socialapp.presentation.screen.auth.signIn.state.SignInUIState
import com.skymilk.socialapp.presentation.util.MessageEvent
import com.skymilk.socialapp.presentation.util.sendEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authUseCase: AuthUseCase
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

            val authResultData = authUseCase.signIn(
                email = uiState.email,
                password = uiState.password
            )

            authState.update {
                when (authResultData) {
                    is Result.Error -> {
                        val message = authResultData.message.toString()
                        sendEvent(MessageEvent.Toast(message = message))

                        AuthState.Error(message)
                    }

                    is Result.Success -> AuthState.Authenticated
                }
            }
        }
    }
}