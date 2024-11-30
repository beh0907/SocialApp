package com.skymilk.socialapp.android.presentation.screen.main.follows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skymilk.socialapp.android.presentation.screen.destinations.ProfileDestination
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Destination
@Composable
fun Following(
    navigator: DestinationsNavigator,
    userId: Int,
) {
    val followsViewModel: FollowsViewModel = koinViewModel(parameters = { parametersOf(userId, 2) })
    val followsUsersState by followsViewModel.followsUsersState.collectAsStateWithLifecycle()

    FollowsScreen(
        followsUsersState = followsUsersState,
        onEvent = followsViewModel::onEvent,
        onNavigateToProfile = {
            navigator.navigate(ProfileDestination(it))
        }
    )
}