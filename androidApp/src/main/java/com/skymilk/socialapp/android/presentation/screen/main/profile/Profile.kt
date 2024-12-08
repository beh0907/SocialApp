package com.skymilk.socialapp.android.presentation.screen.main.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val postsState by profileViewModel.postsState.collectAsStateWithLifecycle()

    ProfileScreen(
        profileState = profileState,
        postsState = postsState,
        onEvent = profileViewModel::onEvent,
        onNavigateToProfile = { navigator.navigate(ProfileEditDestination(userId)) },
        onFollowersClick = { navigator.navigate(FollowersDestination(userId)) },
        onFollowingClick = { navigator.navigate(FollowingDestination(userId)) },
        onPostClick = {
            navigator.navigate(PostDetailDestination(it.postId))
        },
        onLikeClick = {},
        onCommentClick = {}
    )

}