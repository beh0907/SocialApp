package com.skymilk.socialapp.store.presentation.screen.main.postDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import app.cash.paging.compose.collectAsLazyPagingItems
import com.skymilk.socialapp.store.presentation.navigation.routes.Routes
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PostDetailRoute(
    navigator: NavHostController,
    postId: Long,
    userId: Long
) {
    val postDetailViewModel: PostDetailViewModel =
        koinViewModel(parameters = { parametersOf(postId) })

    val postState by postDetailViewModel.postState.collectAsStateWithLifecycle()
    val postComments = postDetailViewModel.postComments.collectAsLazyPagingItems()
    val postDetailUiState = postDetailViewModel.postDetailUiState

    PostDetailScreen(
        userId = userId,
        postDetailUiState = postDetailUiState,
        postDetailState = postState,
        postComments = postComments,
        onEvent = postDetailViewModel::onEvent,
        onNavigateToProfile = { navigator.navigate(Routes.ProfileScreen(it)) },
        onNavigateToPostEdit = { navigator.navigate(Routes.PostEditScreen(post = it)) },
        onNavigateToBack = { navigator.navigateUp() },
    )
}