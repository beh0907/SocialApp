package com.skymilk.socialapp.android.presentation.screen.main.postDetail.component

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.paging.compose.LazyPagingItems
import com.skymilk.socialapp.domain.model.PostComment

fun LazyListScope.postCommentsList(
    postComments: LazyPagingItems<PostComment>,
    onNavigateToProfile: (Long) -> Unit,
    onCommentMoreClick: (PostComment) -> Unit
) {
    items(count = postComments.itemCount) {
        postComments[it]?.let { comment ->
            HorizontalDivider()

            CommentItem(
                comment = comment,
                onNavigateToProfile = onNavigateToProfile,
                onCommentMoreClick = { onCommentMoreClick(comment) }
            )
        }
    }
}