package com.skymilk.socialapp.android.presentation.screen.main.postDetail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skymilk.socialapp.R
import com.skymilk.socialapp.android.presentation.common.component.CircleImage
import com.skymilk.socialapp.android.ui.theme.LargeSpacing
import com.skymilk.socialapp.android.ui.theme.MediumSpacing
import com.skymilk.socialapp.domain.model.PostComment

@Composable
fun CommentItem(
    modifier: Modifier = Modifier,
    comment: PostComment,
    onNavigateToProfile: (Long) -> Unit,
    onCommentMoreClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(LargeSpacing),
        horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
    ) {
        CircleImage(
            modifier = Modifier.size(30.dp),
            image = comment.userImageUrl,
            onClick = { onNavigateToProfile(comment.userId) }
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
            ) {
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = comment.userName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    modifier = Modifier
                        .alignByBaseline()
                        .weight(1f),
                    text = comment.createAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )

                Icon(
                    modifier = Modifier.clickable { onCommentMoreClick },
                    painter = painterResource(id = R.drawable.round_more_horiz_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            Text(
                text = comment.content, style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}