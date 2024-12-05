package com.skymilk.socialapp.android.presentation.screen.main.postDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skymilk.socialapp.R
import com.skymilk.socialapp.android.presentation.common.component.PostItem
import com.skymilk.socialapp.android.presentation.common.dummy.Comment
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.component.CommentItem
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.state.CommentsState
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.state.PostState
import com.skymilk.socialapp.android.ui.theme.LargeSpacing
import com.skymilk.socialapp.android.ui.theme.MediumSpacing
import com.skymilk.socialapp.android.ui.theme.White

@Composable
fun PostDetailScreen(
    modifier: Modifier = Modifier,
    postState: PostState,
    commentsState: CommentsState,
    onEvent: (PostDetailEvent) -> Unit,
    onNavigateToProfile: (Int) -> Unit,
    onCommentMoreClick: (Comment) -> Unit,
    onAddCommentClick: () -> Unit,
) {
    when {
        postState is PostState.Loading || commentsState is CommentsState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }


        postState is PostState.Success && commentsState is CommentsState.Success -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                item(key = "post") {
                    PostItem(
                        post = postState.post,
                        onNavigateToPost = { },
                        onNavigateToProfile = onNavigateToProfile,
                        onLikeClick = { },
                        onCommentClick = { },
                        isDetailScreen = true
                    )
                }

                item(key = "commentHeaderSection") {
                    HeaderSection(
                        onAddCommentClick = onAddCommentClick
                    )
                }


                items(items = commentsState.comments, key = { it.id }) { comment ->
                    HorizontalDivider()

                    CommentItem(
                        comment = comment,
                        onNavigateToProfile = onNavigateToProfile,
                        onCommentMoreClick = { onCommentMoreClick(comment) }
                    )
                }
            }
        }

        else -> Unit
    }
}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    onAddCommentClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(LargeSpacing),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.comments_header_label),
            style = typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onAddCommentClick }
                .padding(vertical = MediumSpacing, horizontal = LargeSpacing),
            text = stringResource(R.string.add_comment_button_label),
            style = typography.bodyMedium,
            color = White
        )
    }

}