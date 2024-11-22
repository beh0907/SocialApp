package com.skymilk.socialapp.android.store.presentation.screen.auth.signUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
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
        }
    }

    private fun updateUserName(username: String) {
        uiState = uiState.copy(username = username)
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
}