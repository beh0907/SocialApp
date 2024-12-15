package com.skymilk.socialapp.android.presentation.screen.main.postDetail

import androidx.compose.animation.animateContentSize
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.skymilk.socialapp.R
import com.skymilk.socialapp.android.presentation.common.component.PostItem
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.component.CommentOptionBottomSheet
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.component.SendCommentButton
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.component.postCommentsList
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.state.PostDetailState
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.state.PostUiState
import com.skymilk.socialapp.android.ui.theme.LargeSpacing
import com.skymilk.socialapp.android.ui.theme.MediumSpacing
import com.skymilk.socialapp.android.ui.theme.SmallSpacing
import com.skymilk.socialapp.domain.model.PostComment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    modifier: Modifier = Modifier,
    postDetailUiState: PostUiState,
    postDetailState: PostDetailState,
    postComments: LazyPagingItems<PostComment>,
    onEvent: (PostDetailEvent) -> Unit,
    onNavigateToProfile: (Long) -> Unit,
) {
    var selectedPostComment by rememberSaveable(stateSaver = postCommentSaver) {
        mutableStateOf(null)
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
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    item(key = "post") {
                        PostItem(
                            post = postDetailState.post,
                            onClickPost = { },
                            onNavigateToProfile = onNavigateToProfile,
                            onLikeClick = { onEvent(PostDetailEvent.LikePost(it)) },
                            isDetailScreen = true
                        )
                    }

                    item(key = "commentHeaderSection") {
                        CommentHeaderSection()
                    }

                    postCommentsList(
                        postComments = postComments,
                        onNavigateToProfile = onNavigateToProfile,
                        onCommentMoreClick = { selectedPostComment = it }
                    )
                }

                BottomSection(
                    comment = postDetailUiState.content,
                    onCommentChange = { onEvent(PostDetailEvent.ChangeComment(it)) },
                    onSendClick = { onEvent(PostDetailEvent.SendComment) }
                )
            }

            //선택한 댓글이 있을 경우 바텀 시트 팝업
            selectedPostComment?.let { postComment ->
                CommentOptionBottomSheet(
                    comment = postComment,
                    shouldDeleteComment = postComment.userId == postDetailState.post.userId || postComment.isOwner, // 게시글 작성자 or 댓글 작성자만 지울 수 있다
                    onDeletePostComment = { onEvent(PostDetailEvent.RemoveComment(it)) },
                    onNavigateToProfile = onNavigateToProfile,
                    onResetSelectedPostComment = { selectedPostComment = null }
                )
            }
        }

        else -> Unit
    }
}


@Composable
fun CommentHeaderSection(
    modifier: Modifier = Modifier
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
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
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
            .background(color = MaterialTheme.colorScheme.surface)
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
                        color = MaterialTheme.colorScheme.background,
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

private val postCommentSaver = Saver<PostComment?, Any>(
    save = { postComment ->
        if (postComment != null) {
            mapOf(
                "commentId" to postComment.commentId,
                "content" to postComment.content,
                "postId" to postComment.postId,
                "userId" to postComment.userId,
                "userName" to postComment.userName,
                "userImageUrl" to postComment.userImageUrl,
                "createAt" to postComment.createAt,
            )
        } else null
    },
    restore = { savedValue ->
        val map = savedValue as Map<*, *>
        PostComment(
            commentId = map["commentId"] as Long,
            content = map["content"] as String,
            postId = map["postId"] as Long,
            userId = map["userId"] as Long,
            userName = map["userName"] as String,
            userImageUrl = map["userImageUrl"] as String?,
            createAt = map["createAt"] as String
        )
    }
)