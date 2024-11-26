package com.skymilk.socialapp.android.presentation.screen.main.profile

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skymilk.socialapp.android.presentation.screen.destinations.PostDetailDestination
import com.skymilk.socialapp.android.presentation.screen.main.profile.ProfileScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Destination
@Composable
fun Profile(
    navigator: DestinationsNavigator,
    userId: Int
) {
    val profileViewModel: ProfileViewModel =
        koinViewModel(parameters = { parametersOf(userId) })

    ProfileScreen(
        userInfoUiState = profileViewModel.userInfoUiState,
        userPostsUiState = profileViewModel.userPostsUiState,
        onEvent = profileViewModel::onEvent,
        onFollowersClick = {},
        onFollowingClick = {},
        onFollowClick = {},
        onPostClick = {
            navigator.navigate(PostDetailDestination(it.id))
        },
        onLikeClick = {},
        onCommentClick = {}
    )

}