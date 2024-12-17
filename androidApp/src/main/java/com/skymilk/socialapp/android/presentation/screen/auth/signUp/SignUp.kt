package com.skymilk.socialapp.android.presentation.screen.auth.signUp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.skymilk.socialapp.android.presentation.navigation.routes.Routes
import org.koin.androidx.compose.koinViewModel


@Composable
fun SignUp(
    navigator: NavHostController
) {
    val viewModel: SignUpViewModel = koinViewModel()
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    SignUpScreen(
        uiState = viewModel.uiState,
        authState = authState,
        onEvent = viewModel::onEvent,
        onNavigateToSignIn = {
            navigator.navigate(Routes.SignInScreen) {
                popUpTo(Routes.SignUpScreen) {
                    inclusive = true
                }
            }
        },
        onNavigateToHome = {
            navigator.navigate(Routes.HomeScreen) {
                popUpTo(Routes.SignUpScreen) {
                    inclusive = true
                }
            }
        }
    )
}