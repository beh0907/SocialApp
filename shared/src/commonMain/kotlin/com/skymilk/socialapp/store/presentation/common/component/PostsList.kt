package com.skymilk.socialapp.store.presentation.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import com.skymilk.socialapp.SharedRes
import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.ui.theme.MediumSpacing
import dev.icerock.moko.resources.compose.stringResource

//게시글 목록
fun LazyListScope.postsList(
    posts: LazyPagingItems<Post>,
    onClickPost: (Post) -> Unit,
    onNavigateToProfile: (Long) -> Unit,
    onLikeClick: (Post) -> Unit,
) {
    items(count = posts.itemCount) { index ->
        posts[index]?.let { post ->
            PostItem(
                post = post,
                onClickPost = onClickPost,
                onClickPostMore = { },
                onNavigateToProfile = onNavigateToProfile,
                onLikeClick = onLikeClick
            )
        }

        if (index != posts.itemCount - 1)
            Spacer(
                modifier = Modifier.fillMaxWidth().height(MediumSpacing)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
    }

    errorHandler(posts)
}

private fun LazyListScope.errorHandler(posts: LazyPagingItems<Post>) {
    posts.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item { LoadingItem() }
            }

            loadState.refresh is LoadState.Error -> {
                item {
                    ErrorItem(
                        message = stringResource(SharedRes.strings.error_load_posts_text),
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
                        message = stringResource(SharedRes.strings.error_load_posts_text),
                        onRetry = { retry() }
                    )
                }
            }
        }
    }
}