package com.skymilk.socialapp.android.presentation.screen.main.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skymilk.socialapp.android.presentation.common.dummy.FollowsUser
import com.skymilk.socialapp.android.ui.theme.LargeSpacing

@Composable
fun OnBoardingUserList(
    modifier: Modifier = Modifier,
    users: List<FollowsUser>,
    onUserClick: (FollowsUser) -> Unit,
    onFollowClick: (FollowsUser, Boolean) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(LargeSpacing),
        contentPadding = PaddingValues(horizontal = LargeSpacing)
    ) {

        items(users, key = { it.seq }) {
            OnBoardingUserItem(
                followsUser = it,
                onUserClick = onUserClick,
                onFollowClick = onFollowClick
            )
        }
    }
}