package com.skymilk.socialapp.android.presentation.screen.auth.signUp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skymilk.socialapp.android.presentation.screen.destinations.HomeDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.SignInDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.SignUpDestination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun SignUp(
    navigator: DestinationsNavigator
) {
    val viewModel: SignUpViewModel = koinViewModel()
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    SignUpScreen(
        uiState = viewModel.uiState,
        authState = authState,
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

@Composable
fun SignUp2(
    navigator: NavHostController
) {
    val viewModel: SignUpViewModel = koinViewModel()
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    SignUpScreen(
        uiState = viewModel.uiState,
        authState = authState,
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