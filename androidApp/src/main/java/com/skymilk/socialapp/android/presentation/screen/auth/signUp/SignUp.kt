package com.skymilk.socialapp.android.presentation.screen.auth.signUp

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skymilk.socialapp.android.presentation.screen.destinations.HomeDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.SignInDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.SignUpDestination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun SignUp(
    navigator:DestinationsNavigator
) {
    val viewModel: SignUpViewModel = koinViewModel()

    SignUpScreen(
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent,
        onNavigateToSignIn = {
            navigator.navigate(SignInDestination) {
                popUpTo(SignUpDestination) {
                    inclusive = true
                }
            }
        },
        onNavigateToHome = {
            navigator.navigate(HomeDestination) {
                popUpTo(SignUpDestination) {
                    inclusive = true
                }
            }
        }
    )
}