package com.skymilk.socialapp.store.presentation.screen.main.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skymilk.socialapp.store.domain.model.FollowsUser
import com.skymilk.socialapp.ui.theme.LargeSpacing

@Composable
fun OnBoardingUserList(
    modifier: Modifier = Modifier,
    users: List<FollowsUser>,
    onNavigateToProfile: (FollowsUser) -> Unit,
    onFollowClick: (FollowsUser) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(LargeSpacing),
        contentPadding = PaddingValues(horizontal = LargeSpacing)
    ) {

        items(users, key = { it.id }) {
            OnBoardingUserItem(
                followsUser = it,
                onNavigateToProfile = onNavigateToProfile,
                onFollowClick = onFollowClick
            )
        }
    }
}