package com.skymilk.socialapp.android.store.presentation.screen.auth.signIn

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination()
@Composable
fun SignIn(
    navigator:DestinationsNavigator
) {
    val viewModel:SignInViewModel = koinViewModel()

    SignInScreen(
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent
    )
}