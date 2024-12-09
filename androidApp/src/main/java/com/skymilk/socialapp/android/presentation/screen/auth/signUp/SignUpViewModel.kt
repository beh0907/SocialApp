package com.skymilk.socialapp.android.presentation.screen.auth.signUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.android.presentation.common.state.AuthState
import com.skymilk.socialapp.android.presentation.screen.auth.signUp.state.SignUpUIState
import com.skymilk.socialapp.android.presentation.util.MessageEvent
import com.skymilk.socialapp.android.presentation.util.sendEvent
import com.skymilk.socialapp.domain.usecase.auth.AuthUseCase
import com.skymilk.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    var uiState by mutableStateOf(SignUpUIState())
        private set

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState = _authState

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.UpdateUserName -> {
                updateUserName(event.username)
            }

            is SignUpEvent.UpdateEmail -> {
                updateEmail(event.email)
            }

            is SignUpEvent.UpdatePassword -> {
                updatePassword(event.password)
            }

            is SignUpEvent.UpdatePasswordConfirm -> {
                updatePasswordConfirm(event.password)
            }

            is SignUpEvent.SignUp -> {
                signUp()
            }
        }
    }

    private fun updateUserName(username: String) {
        uiState = uiState.copy(name = username)
    }

    private fun updateEmail(username: String) {
        uiState = uiState.copy(email = username)
    }

    private fun updatePassword(username: String) {
        uiState = uiState.copy(password = username)
    }

    private fun updatePasswordConfirm(username: String) {
        uiState = uiState.copy(passwordConfirm = username)
    }

    private fun signUp() {
        viewModelScope.launch {
            authState.update { AuthState.Loading }

            val authResultData = authUseCase.signUpUseCase(
                name = uiState.name,
                email = uiState.email,
                password = uiState.password,
                passwordConfirm = uiState.passwordConfirm
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