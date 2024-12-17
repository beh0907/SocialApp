package com.skymilk.socialapp.android.presentation.screen.auth.signIn

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.skymilk.socialapp.android.presentation.navigation.routes.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignIn(
    navigator: NavHostController,
) {
    val viewModel: SignInViewModel = koinViewModel()
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    SignInScreen(
        uiState = viewModel.uiState,
        authState = authState,
        onEvent = viewModel::onEvent,
        onNavigateToSignUp = {
            navigator.navigate(Routes.SignUpScreen)
        },
        onNavigateToHome = {
            navigator.navigate(Routes.HomeScreen) {
                popUpTo(Routes.SignInScreen) {
                    inclusive = true
                }
            }
        }
    )
}