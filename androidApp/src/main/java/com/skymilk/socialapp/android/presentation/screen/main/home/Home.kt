package com.skymilk.socialapp.android.presentation.screen.main.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
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
    val feedPosts = homeViewModel.feedPosts.collectAsLazyPagingItems()

    HomeScreen(
        onBoardingState = onBoardingUiState,
        feedPosts = feedPosts,
        onEvent = homeViewModel::onEvent,
        onNavigateToPostDetail = {
            navigator.navigate(PostDetailDestination(it.postId))
        },
        onNavigateToProfile = {
            navigator.navigate(ProfileDestination(it))
        }
    )
}

@Composable
fun Home2(
    navigator: NavHostController
) {
    val homeViewModel: HomeViewModel = koinViewModel()

    val onBoardingUiState by homeViewModel.onBoardingUiState.collectAsStateWithLifecycle()
    val feedPosts = homeViewModel.feedPosts.collectAsLazyPagingItems()

    HomeScreen(
        onBoardingState = onBoardingUiState,
        feedPosts = feedPosts,
        onEvent = homeViewModel::onEvent,
        onNavigateToPostDetail = {
            navigator.navigate(PostDetailDestination(it.postId))
        },
        onNavigateToProfile = {
            navigator.navigate(ProfileDestination(it))
        }
    )
}