package com.skymilk.socialapp.store.presentation.screen.main.follows

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paging.compose.LazyPagingItems
import com.skymilk.socialapp.store.domain.model.FollowsUser
import com.skymilk.socialapp.store.presentation.screen.main.follows.component.FollowsItem

@Composable
fun FollowsScreen(
    modifier: Modifier = Modifier,
    follows: LazyPagingItems<FollowsUser>,
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