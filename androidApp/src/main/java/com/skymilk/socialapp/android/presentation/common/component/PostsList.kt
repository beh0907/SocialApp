package com.skymilk.socialapp.android.presentation.common.component

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.skymilk.socialapp.domain.model.Post

//게시물 목록
fun LazyListScope.postsList(
    posts: LazyPagingItems<Post>,
    onClickPost: (Post) -> Unit,
    onNavigateToProfile: (Long) -> Unit,
    onLikeClick: (Post) -> Unit,
) {
//    val handlePagingResult = handlePagingResult(posts = posts)
    items(count = posts.itemCount) {
        posts[it]?.let { post ->
            PostItem(
                post = post,
                onClickPost = onClickPost,
                onNavigateToProfile = onNavigateToProfile,
                onLikeClick = onLikeClick
            )
        }
    }
}

//페이징 상태 처리
//@Composable
//fun handlePagingResult(
//    posts: LazyPagingItems<Post>
//): Boolean {
//    val loadState = posts.loadState
//    val error = when {
//        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
//        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
//        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
//        else -> null
//    }
//
//    return when {
//        loadState.refresh is LoadState.Loading -> {
//            ShimmerEffect() // 로딩 중이라면 로딩 효과 출력
//            false
//        }
//
//        error != null -> {
//            EmptyScreen(
//                error = error
//            ) // 에러가 발생했다면 빈 화면
//            false
//        }
//
//        posts.itemCount == 0 -> { // 페이징 기사가 없다면
//            EmptyScreen()
//            false
//        }
//
//        else -> true // 결과 리턴
//    }
//}