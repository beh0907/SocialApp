package com.skymilk.socialapp.android.store.presentation.screen.auth.signIn

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skymilk.socialapp.android.store.presentation.screen.destinations.HomeScreenDestination
import com.skymilk.socialapp.android.store.presentation.screen.destinations.SignUpDestination
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun SignIn(
    navigator: DestinationsNavigator
) {
    val viewModel: SignInViewModel = koinViewModel()

    SignInScreen(
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent,
        onNavigateToSignUp = { navigator.navigate(SignUpDestination) },
        onNavigateToHome = { navigator.navigate(HomeScreenDestination) }
    )
}