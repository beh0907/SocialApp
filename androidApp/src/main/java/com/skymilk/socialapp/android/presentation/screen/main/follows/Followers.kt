package com.skymilk.socialapp.android.presentation.screen.main.follows

import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skymilk.socialapp.android.presentation.screen.destinations.ProfileDestination
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Destination
@Composable
fun Followers(
    navigator: DestinationsNavigator,
    userId: Long,
) {
    val followsViewModel: FollowsViewModel = koinViewModel(parameters = { parametersOf(userId, 1) })
    val followsState = followsViewModel.follows.collectAsLazyPagingItems()

    FollowsScreen(
        follows = followsState,
        onEvent = followsViewModel::onEvent,
        onNavigateToProfile = {
            navigator.navigate(ProfileDestination(it))
        }
    )
}