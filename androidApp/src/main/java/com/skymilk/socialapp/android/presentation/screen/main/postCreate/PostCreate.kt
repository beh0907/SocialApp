package com.skymilk.socialapp.android.presentation.screen.main.postCreate

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun PostCreate(
    navigator: DestinationsNavigator,
) {
    val postCreateViewModel: PostCreateViewModel = koinViewModel()

    val uiState = postCreateViewModel.uiState

    PostCreateScreen(
        uiState = uiState,
        onEvent = postCreateViewModel::onEvent,
        onNavigateToBack = { navigator.navigateUp() }
    )
}

@Composable
fun PostCreate2(
    navigator: NavHostController,
) {
    val postCreateViewModel: PostCreateViewModel = koinViewModel()

    val uiState = postCreateViewModel.uiState

    PostCreateScreen(
        uiState = uiState,
        onEvent = postCreateViewModel::onEvent,
        onNavigateToBack = { navigator.navigateUp() }
    )
}