package com.skymilk.socialapp.store.presentation.screen.main.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import app.cash.paging.compose.collectAsLazyPagingItems
import com.skymilk.socialapp.store.presentation.navigation.routes.Routes
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoute(
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