package com.skymilk.socialapp.android.presentation.screen.main.home

import androidx.compose.runtime.Composable
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

    HomeScreen(
        onBoardingUiState = homeViewModel.onBoardingUiState,
        postsUiState = homeViewModel.postsUiState,
        onEvent = homeViewModel::onEvent,
        onPostClick = {
            navigator.navigate(PostDetailDestination(it.id))
        },
        onProfileClick = {
            navigator.navigate(ProfileDestination(it))
        },
        onLikeClick = {},
        onCommentClick = {},
    )
}