package com.skymilk.socialapp.android.presentation.screen.main.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skymilk.socialapp.R
import com.skymilk.socialapp.android.presentation.common.component.CircleImage
import com.skymilk.socialapp.android.presentation.common.component.FollowsButton
import com.skymilk.socialapp.android.presentation.common.dummy.FollowsUser
import com.skymilk.socialapp.android.ui.theme.MediumSpacing
import com.skymilk.socialapp.android.ui.theme.SmallSpacing

@Composable
fun OnBoardingUserItem(
    modifier: Modifier = Modifier,
    followsUser: FollowsUser,
    onUserClick: (FollowsUser) -> Unit,
    isFollowing: Boolean = false,
    onFollowClick: (FollowsUser, Boolean) -> Unit
) {

    Card(
        modifier = modifier
            .width(130.dp),
        onClick = { onUserClick(followsUser) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MediumSpacing),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircleImage(
                modifier = Modifier.size(50.dp),
                imageUrl = followsUser.profileUrl,
                onClick = { onUserClick(followsUser) }
            )

            Spacer(Modifier.height(SmallSpacing))

            Text(
                text = followsUser.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(SmallSpacing))

            FollowsButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = R.string.follow_button_text,
                onFollowClick = {
                    onFollowClick(followsUser, !isFollowing)
                },
                isOutline = false
            )
        }
    }

}