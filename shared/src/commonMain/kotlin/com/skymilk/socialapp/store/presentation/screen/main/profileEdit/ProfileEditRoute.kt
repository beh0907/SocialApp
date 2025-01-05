package com.skymilk.socialapp.store.presentation.screen.main.profileEdit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProfileEditRoute(
    navigator: NavHostController,
    userId: Long
) {
    val profileEditViewModel: ProfileEditViewModel =
        koinViewModel(parameters = { parametersOf(userId) })

    val uiState = profileEditViewModel.uiState
    val profileState by profileEditViewModel.profileState.collectAsStateWithLifecycle()

    ProfileEditScreen(
        uiState = uiState,
        profileState = profileState,
        onEvent = profileEditViewModel::onEvent
    )
}