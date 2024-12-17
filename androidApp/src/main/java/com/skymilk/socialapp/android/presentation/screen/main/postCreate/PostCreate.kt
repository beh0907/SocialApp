package com.skymilk.socialapp.android.presentation.screen.main.postCreate

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun PostCreate(
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