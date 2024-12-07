package com.skymilk.socialapp.android.presentation.screen.main.follows

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.skymilk.socialapp.android.presentation.screen.main.follows.component.FollowsItem
import com.skymilk.socialapp.android.presentation.screen.main.follows.state.FollowsState

@Composable
fun FollowsScreen(
    modifier: Modifier = Modifier,
    followsUsersState: FollowsState,
    onEvent: (FollowsEvent) -> Unit,
    onNavigateToProfile: (Long) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        when (followsUsersState) {
            is FollowsState.Initial -> Unit
            is FollowsState.Loading -> CircularProgressIndicator()
            is FollowsState.Success -> {
                LazyColumn(modifier = modifier.fillMaxSize()) {
                    items(items = followsUsersState.followsUsers, key = { it.id }) {
                        FollowsItem(
                            user = it,
                            onItemClick = {
                                onNavigateToProfile(it.id)
                            }
                        )
                    }
                }
            }

            else -> Unit
        }

    }
}