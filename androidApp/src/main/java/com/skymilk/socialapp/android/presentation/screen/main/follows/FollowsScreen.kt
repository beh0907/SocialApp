package com.skymilk.socialapp.android.presentation.screen.main.follows

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.skymilk.socialapp.android.presentation.screen.main.follows.component.FollowsItem
import com.skymilk.socialapp.domain.model.FollowsUser

@Composable
fun FollowsScreen(
    modifier: Modifier = Modifier,
    follows: LazyPagingItems<FollowsUser>,
    onEvent: (FollowsEvent) -> Unit,
    onNavigateToProfile: (Long) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(count = follows.itemCount) {
            val user = follows[it] ?: return@items

            FollowsItem(
                user = user,
                onItemClick = {
                    onNavigateToProfile(user.id)
                }
            )
        }
    }
}