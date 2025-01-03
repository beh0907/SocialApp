package com.skymilk.socialapp.store.presentation.screen.main.postEdit

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.presentation.screen.main.postCreate.PostCreateScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PostEdit(
    navigator: NavHostController,
    post:Post
) {
    val postEditViewModel: PostEditViewModel = koinViewModel(parameters = { parametersOf(post) })

    val uiState = postEditViewModel.uiState

    PostEditScreen(
        uiState = uiState,
        onEvent = postEditViewModel::onEvent,
        onNavigateToBack = { navigator.navigateUp() }
    )
}