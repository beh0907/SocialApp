package com.skymilk.socialapp.android.presentation.screen.auth.signIn

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skymilk.socialapp.android.presentation.screen.destinations.HomeDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.SignInDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.SignUpDestination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun SignIn(
    navigator: DestinationsNavigator
) {
    val viewModel: SignInViewModel = koinViewModel()

    SignInScreen(
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent,
        onNavigateToSignUp = {
            navigator.navigate(SignUpDestination) {
                popUpTo(SignInDestination) {
                    inclusive = true
                }
            }
        },
        onNavigateToHome = {
            navigator.navigate(HomeDestination) {
                popUpTo(SignInDestination) {
                    inclusive = true
                }
            }
        }
    )
}