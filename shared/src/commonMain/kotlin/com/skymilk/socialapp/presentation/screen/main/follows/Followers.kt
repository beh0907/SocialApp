package com.skymilk.socialapp.presentation.screen.main.follows

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import app.cash.paging.compose.collectAsLazyPagingItems
import com.skymilk.socialapp.presentation.navigation.routes.Routes
import org.koin.compose.viewmodel.koinViewModel
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
        onNavigateToProfile = {
            navigator.navigate(Routes.ProfileScreen(it))
        }
    )
}