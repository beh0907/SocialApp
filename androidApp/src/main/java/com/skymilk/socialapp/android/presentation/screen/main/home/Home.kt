package com.skymilk.socialapp.android.presentation.screen.main.home

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun Home(
    navigator: DestinationsNavigator
) {
    val homeViewModel: HomeViewModel = koinViewModel()

    HomeScreen(
        onBoardingUiState = homeViewModel.onBoardingUiState,
        postsUiState = homeViewModel.postsUiState,
        onEvent = homeViewModel::onEvent,
        onPostClick = {},
        onProfileClick = {},
        onLikeClick = {},
        onCommentClick = {},
    )
}