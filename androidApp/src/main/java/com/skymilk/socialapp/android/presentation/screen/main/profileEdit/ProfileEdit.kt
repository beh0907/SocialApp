package com.skymilk.socialapp.android.presentation.screen.main.profileEdit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Destination
@Composable
fun ProfileEdit(
    navigator: DestinationsNavigator,
    userId: Long
) {
    val profileEditViewModel: ProfileEditViewModel = koinViewModel(parameters = { parametersOf(userId) })

    val uiState = profileEditViewModel.uiState
    val profileState by profileEditViewModel.profileState.collectAsStateWithLifecycle()

    ProfileEditScreen(
        uiState = uiState,
        profileState = profileState,
        onEvent = profileEditViewModel::onEvent,
    )
}