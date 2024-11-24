package com.skymilk.socialapp.android.store.presentation.screen.auth.signUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.android.store.presentation.util.Event
import com.skymilk.chatapp.store.presentation.utils.sendEvent
import com.skymilk.socialapp.domain.usecase.auth.SignUpUseCase
import com.skymilk.socialapp.util.Result
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    var uiState by mutableStateOf(SignUpUIState())
        private set

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
            uiState = uiState.copy(isAuthenticating = true)

            val authResultData = signUpUseCase(
                name = uiState.name,
                email = uiState.email,
                password = uiState.password,
                passwordConfirm = uiState.passwordConfirm
            )

            uiState = when(authResultData) {
                is Result.Success -> {
                    uiState.copy(
                        isAuthenticating = false,
                        authenticationSuccess = true
                    )
                }

                is Result.Error -> {
                    val message = authResultData.message.toString()
                    sendEvent(Event.Toast(message = message))

                    uiState.copy(
                        isAuthenticating = false,
                        authErrorMessage = message,
                    )
                }
            }
        }
    }
}