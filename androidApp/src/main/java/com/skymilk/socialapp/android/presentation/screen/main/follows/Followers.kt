package com.skymilk.socialapp.android.presentation.screen.main.follows

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.skymilk.socialapp.android.presentation.navigation.routes.Routes
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun Followers(
    navigator: NavHostController,
    userId: Long,
) {
    val followsViewModel: FollowsViewModel = koinViewModel(parameters = { parametersOf(userId, 1) })
    val followsState = followsViewModel.follows.collectAsLazyPagingItems()

    FollowsScreen(
        follows = followsState,
        onEvent = followsViewModel::onEvent,
        onNavigateToProfile = {
            navigator.navigate(Routes.ProfileScreen(it))
        }
    )
}