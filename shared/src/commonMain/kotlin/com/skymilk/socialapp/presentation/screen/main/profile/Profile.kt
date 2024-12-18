package com.skymilk.socialapp.presentation.screen.main.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import app.cash.paging.compose.collectAsLazyPagingItems
import com.skymilk.socialapp.presentation.navigation.routes.Routes
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun Profile(
    navigator: NavHostController,
    userId: Long
) {
    val profileViewModel: ProfileViewModel = koinViewModel(parameters = { parametersOf(userId) })

    val profileState by profileViewModel.profileState.collectAsStateWithLifecycle()
    val feedPosts = profileViewModel.feedPosts.collectAsLazyPagingItems()

    ProfileScreen(
        profileState = profileState,
        feedPosts = feedPosts,
        onEvent = profileViewModel::onEvent,
        onNavigateToProfileEdit = { navigator.navigate(Routes.ProfileEditScreen(userId)) },
        onNavigateToFollowers = { navigator.navigate(Routes.FollowersScreen(userId)) },
        onNavigateToFollowing = { navigator.navigate(Routes.FollowingScreen(userId)) },
        onNavigateToPostDetail = { navigator.navigate(Routes.PostDetailScreen(it.postId)) }
    )
}