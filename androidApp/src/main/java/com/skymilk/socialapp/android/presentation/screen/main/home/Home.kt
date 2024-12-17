package com.skymilk.socialapp.android.presentation.screen.main.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.skymilk.socialapp.android.presentation.navigation.routes.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun Home(
    navigator: NavHostController
) {
    val homeViewModel: HomeViewModel = koinViewModel()

    val onBoardingUiState by homeViewModel.onBoardingUiState.collectAsStateWithLifecycle()
    val feedPosts = homeViewModel.feedPosts.collectAsLazyPagingItems()

    HomeScreen(onBoardingState = onBoardingUiState,
        feedPosts = feedPosts,
        onEvent = homeViewModel::onEvent,
        onNavigateToPostDetail = {
            navigator.navigate(Routes.PostDetailScreen(it.postId))
        },
        onNavigateToProfile = {
            navigator.navigate(Routes.ProfileScreen(it))
        })
}