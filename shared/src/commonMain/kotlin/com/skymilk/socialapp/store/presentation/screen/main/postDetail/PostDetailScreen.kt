package com.skymilk.socialapp.store.presentation.screen.main.postDetail

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import com.skymilk.socialapp.SharedRes
import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.domain.model.PostComment
import com.skymilk.socialapp.store.presentation.common.component.CustomAlertDialog
import com.skymilk.socialapp.store.presentation.common.component.PostItem
import com.skymilk.socialapp.store.presentation.screen.main.postDetail.component.CommentOptionBottomSheet
import com.skymilk.socialapp.store.presentation.screen.main.postDetail.component.PostOptionBottomSheet
import com.skymilk.socialapp.store.presentation.screen.main.postDetail.component.SendCommentButton
import com.skymilk.socialapp.store.presentation.screen.main.postDetail.component.postCommentsList
import com.skymilk.socialapp.store.presentation.screen.main.postDetail.state.PostDetailState
import com.skymilk.socialapp.store.presentation.screen.main.postDetail.state.PostDetailUiState
import com.skymilk.socialapp.ui.theme.LargeSpacing
import com.skymilk.socialapp.ui.theme.MediumSpacing
import com.skymilk.socialapp.ui.theme.SmallSpacing
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

@Composable
fun PostDetailScreen(
    modifier: Modifier = Modifier,
    userId: Long,
    postDetailUiState: PostDetailUiState,
    postDetailState: PostDetailState,
    postComments: LazyPagingItems<PostComment>,
    onEvent: (PostDetailEvent) -> Unit,
    onNavigateToProfile: (Long) -> Unit,
    onNavigateToPostEdit: (Post) -> Unit,
    onNavigateToBack: () -> Boolean,
) {
    //삭제가 확인 되었다면 뒤로 가기
    LaunchedEffect(postDetailUiState.isDeletedPost) {
        if (postDetailUiState.isDeletedPost) onNavigateToBack()
    }

    when (postDetailState) {
        is PostDetailState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is PostDetailState.Success -> {
            Column(modifier = modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = modifier
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    item(key = "post") {
                        PostItem(
                            post = postDetailState.post,
                            onClickPost = { },
                            onClickPostMore = { onEvent(PostDetailEvent.SetSelectedPostOption(true)) },
                            onNavigateToProfile = onNavigateToProfile,
                            onLikeClick = { onEvent(PostDetailEvent.LikePost(it)) },
                            isDetailScreen = true
                        )
                    }

                    item(key = "commentHeaderSection") {
                        CommentHeaderSection(onRefreshComments = { postComments.refresh() })
                    }

                    postCommentsList(
                        postComments = postComments,
                        onNavigateToProfile = onNavigateToProfile,
                        onCommentMoreClick = {
                            onEvent(PostDetailEvent.SetSelectedPostComment(it))
                        }
                    )
                }

                BottomSection(
                    comment = postDetailUiState.comment,
                    onCommentChange = { onEvent(PostDetailEvent.ChangeComment(it)) },
                    onSendClick = { onEvent(PostDetailEvent.SendComment) }
                )
            }

            //게시글 삭제 다이얼로그
            if (postDetailUiState.isShowRemoveDialog) {
                CustomAlertDialog(
                    title = stringResource(SharedRes.strings.post_delete_dialog_title),
                    message = stringResource(SharedRes.strings.post_delete_dialog_description),
                    onConfirm = { onEvent(PostDetailEvent.RemovePost(post = postDetailState.post)) },
                    onDismiss = { onEvent(PostDetailEvent.SetShowRemoveDialog(false)) }
                )
            }

            //게시글 옵션 선택 바텀 시트 팝업
            if (postDetailUiState.isSelectedPostOption) {
                PostOptionBottomSheet(
                    post = postDetailState.post,
                    onDeletePost = { onEvent(PostDetailEvent.SetShowRemoveDialog(true)) },
                    onNavigateToEditPost = { onNavigateToPostEdit(postDetailState.post) },
                    onResetSelectedPost = { onEvent(PostDetailEvent.SetSelectedPostOption(false)) },
                )
            }

            //선택한 댓글이 있을 경우 바텀 시트 팝업
            postDetailUiState.selectedPostComment?.let { postComment ->
                CommentOptionBottomSheet(
                    comment = postComment,
                    shouldDeleteComment = userId == postDetailState.post.userId || postComment.isOwner, // 게시글 작성자 or 댓글 작성자만 지울 수 있다
                    onDeletePostComment = { onEvent(PostDetailEvent.RemoveComment(it)) },
                    onNavigateToProfile = onNavigateToProfile,
                    onResetSelectedPostComment = {
                        onEvent(
                            PostDetailEvent.SetSelectedPostComment(
                                null
                            )
                        )
                    }
                )
            }
        }

        else -> Unit
    }
}


@Composable
fun CommentHeaderSection(
    modifier: Modifier = Modifier,
    onRefreshComments: () -> Unit
) {
    // 애니메이션 값을 저장
    val rotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    // 클릭 시 애니메이션 실행 함수
    suspend fun startRotation() {
        rotation.stop() // 애니메이션 중단
        rotation.snapTo(0f) // 0으로 초기화
        rotation.animateTo(
            targetValue = 360f, // 1회전
            animationSpec = tween(1000, easing = LinearEasing) // 1000ms 동안 회전
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = LargeSpacing, start = LargeSpacing, end = MediumSpacing),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(SharedRes.strings.comments_header_label),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        IconButton(
            modifier = Modifier.rotate(rotation.value),
            onClick = {
                //회전 애니메이션 시작
                scope.launch { startRotation() }

                //새로고침 API 요청
                onRefreshComments()
            }
        ) {
            Icon(imageVector = Icons.Rounded.Refresh, contentDescription = null)
        }
    }
}

//댓글 입력 영역
@Composable
fun BottomSection(
    modifier: Modifier = Modifier,
    comment: String,
    onCommentChange: (String) -> Unit,
    onSendClick: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .animateContentSize()
    ) {
        HorizontalDivider()

        Row(
            modifier = Modifier
                .padding(horizontal = LargeSpacing, vertical = MediumSpacing),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LargeSpacing)
        ) {
            Box(
                modifier = Modifier
                    .heightIn(min = 35.dp, max = 70.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(percent = 25)
                    )
                    .padding(horizontal = MediumSpacing, vertical = SmallSpacing)
                    .weight(1f)
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                    value = comment,
                    onValueChange = onCommentChange,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = SmallSpacing),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            //placeholder 역할
                            if (comment.isEmpty()) {
                                Text(
                                    text = "메시지 보내기",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }

                            innerTextField()
                        }
                    }
                )
            }

            SendCommentButton(
                sendCommentEnabled = comment.isNotBlank(),
                onSendClick = { onSendClick(comment) }
            )
        }
    }
}