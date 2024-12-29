package com.skymilk.socialapp.store.presentation.screen.main.postDetail.component

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import com.skymilk.socialapp.SharedRes
import com.skymilk.socialapp.store.domain.model.PostComment
import com.skymilk.socialapp.store.presentation.common.component.ErrorItem
import com.skymilk.socialapp.store.presentation.common.component.LoadingItem
import dev.icerock.moko.resources.compose.stringResource

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

    errorHandler(postComments)
}

private fun LazyListScope.errorHandler(postComments: LazyPagingItems<PostComment>) {
    postComments.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item { LoadingItem() }
            }

            loadState.refresh is LoadState.Error -> {
                item {
                    ErrorItem(
                        message = stringResource(SharedRes.strings.error_load_comments_text),
                        onRetry = { retry() }
                    )
                }
            }

            loadState.append is LoadState.Loading -> {
                item { LoadingItem() }
            }

            loadState.append is LoadState.Error -> {
                item {
                    ErrorItem(
                        message = stringResource(SharedRes.strings.error_load_comments_text),
                        onRetry = { retry() }
                    )
                }
            }
        }
    }
}