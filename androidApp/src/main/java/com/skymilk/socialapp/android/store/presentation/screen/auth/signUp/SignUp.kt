package com.skymilk.socialapp.android.store.presentation.screen.auth.signUp

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun SignUp(
    navigator:DestinationsNavigator
) {
    val viewModel:SignUpViewModel = koinViewModel()

    SignUpScreen(
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent
    )
}