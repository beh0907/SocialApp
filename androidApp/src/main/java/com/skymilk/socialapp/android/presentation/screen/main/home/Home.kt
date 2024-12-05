package com.skymilk.socialapp.android.presentation.screen.main.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skymilk.socialapp.android.presentation.screen.destinations.PostDetailDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.ProfileDestination
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun Home(
    navigator: DestinationsNavigator
) {
    val homeViewModel: HomeViewModel = koinViewModel()

    val onBoardingUiState by homeViewModel.onBoardingUiState.collectAsStateWithLifecycle()
    val postsUiState by homeViewModel.postsUiState.collectAsStateWithLifecycle()

    HomeScreen(
        onBoardingState = onBoardingUiState,
        postsState = postsUiState,
        onEvent = homeViewModel::onEvent,
        onNavigateToPost = {
            navigator.navigate(PostDetailDestination(it.id))
        },
        onNavigateToProfile = {
            navigator.navigate(ProfileDestination(it))
        },
        onLikeClick = {},
        onCommentClick = {},
    )
}