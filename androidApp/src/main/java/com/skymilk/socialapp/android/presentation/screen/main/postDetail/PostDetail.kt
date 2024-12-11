package com.skymilk.socialapp.android.presentation.screen.main.postDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Destination
@Composable
fun PostDetail(
    navigator: DestinationsNavigator,
    postId: Long
) {
    val postDetailViewModel: PostDetailViewModel =
        koinViewModel(parameters = { parametersOf(postId) })

    val postState by postDetailViewModel.postState.collectAsStateWithLifecycle()
    val postComments = postDetailViewModel.postComments.collectAsLazyPagingItems()
    val postDetailUiState = postDetailViewModel.postDetailUiState

    PostDetailScreen(
        postDetailUiState = postDetailUiState,
        postDetailState = postState,
        postComments = postComments,
        onEvent = postDetailViewModel::onEvent,
        onNavigateToProfile = { },
    )

}