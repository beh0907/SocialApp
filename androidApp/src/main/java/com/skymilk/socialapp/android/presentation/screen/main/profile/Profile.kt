package com.skymilk.socialapp.android.presentation.screen.main.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skymilk.socialapp.android.presentation.screen.destinations.FollowersDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.FollowingDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.PostDetailDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.ProfileEditDestination
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Destination
@Composable
fun Profile(
    navigator: DestinationsNavigator,
    userId: Long
) {
    val profileViewModel: ProfileViewModel = koinViewModel(parameters = { parametersOf(userId) })

    val profileState by profileViewModel.profileState.collectAsStateWithLifecycle()
    val feedPosts = profileViewModel.feedPosts.collectAsLazyPagingItems()

    ProfileScreen(
        profileState = profileState,
        feedPosts = feedPosts,
        onEvent = profileViewModel::onEvent,
        onNavigateToProfileEdit = { navigator.navigate(ProfileEditDestination(userId)) },
        onNavigateToFollowers = { navigator.navigate(FollowersDestination(userId)) },
        onNavigateToFollowing = { navigator.navigate(FollowingDestination(userId)) },
        onNavigateToPostDetail = { navigator.navigate(PostDetailDestination(it.postId)) }
    )
}