package com.skymilk.socialapp.android.presentation.screen.main.postDetail

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Destination
@Composable
fun PostDetail(
    navigator: DestinationsNavigator,
    postId: String
) {
    val postDetailViewModel: PostDetailViewModel =
        koinViewModel(parameters = { parametersOf(postId) })

    PostDetailScreen(
        postUiState = postDetailViewModel.postUiState,
        commentsUiState = postDetailViewModel.commentsUiState,
        onEvent = postDetailViewModel::onEvent,
        onProfileClick = { },
        onCommentMoreClick = { },
        onAddCommentClick = { },
    )

}