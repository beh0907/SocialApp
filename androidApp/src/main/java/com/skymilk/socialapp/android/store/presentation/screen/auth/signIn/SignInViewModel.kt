package com.skymilk.socialapp.android.store.presentation.screen.auth.signIn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {
    var uiState by mutableStateOf(SignInUIState())
        private set

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.UpdateEmail -> {
                updateEmail(event.email)
            }

            is SignInEvent.UpdatePassword -> {
                updatePassword(event.password)
            }
        }
    }

    private fun updateEmail(username: String) {
        uiState = uiState.copy(email = username)
    }

    private fun updatePassword(username: String) {
        uiState = uiState.copy(password = username)
    }
}