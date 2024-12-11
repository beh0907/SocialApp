package com.skymilk.socialapp.android.presentation.screen.main.postDetail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.skymilk.socialapp.R
import com.skymilk.socialapp.android.ui.theme.LargeSpacing
import com.skymilk.socialapp.domain.model.PostComment
import kotlinx.coroutines.launch

//댓글 옵션 아이콘 버튼 연동 바텀시트
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentOptionBottomSheet(
    modifier: Modifier = Modifier,
    comment: PostComment,
    shouldDeleteComment: Boolean,
    onDeletePostComment: (PostComment) -> Unit,
    onNavigateToProfile: (Long) -> Unit,
    onResetSelectedPostComment: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {
            //선택한 댓글 초기화
            onResetSelectedPostComment()

            //시트 숨기기
            scope.launch { sheetState.hide() }
        },
        sheetState = sheetState,
    ) {
        Column(
            modifier = modifier
        ) {
            //헤더
            Text(
                modifier = Modifier.padding(LargeSpacing),
                text = stringResource(R.string.comment_option_title),
                style = MaterialTheme.typography.titleMedium,
            )

            HorizontalDivider()

            //댓글 삭제 메뉴
            ListItem(
                modifier = Modifier.clickable(
                    enabled = shouldDeleteComment,
                    onClick = {
                        //시트 숨기기
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            //댓글 삭제 이벤트 호출
                            onDeletePostComment(comment)

                            //선택한 댓글 초기화
                            onResetSelectedPostComment()
                        }
                    }
                ),
                headlineContent = {
                    Text(
                        text = stringResource(R.string.delete_comment_option),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null,
                    )
                }
            )

            //프로필 이동
            ListItem(
                modifier = Modifier.clickable(
                    enabled = shouldDeleteComment,
                    onClick = {
                        //시트 숨기기
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            //프로필 화면 이동
                            onNavigateToProfile(comment.userId)

                            //선택한 댓글 초기화
                            onResetSelectedPostComment()
                        }
                    }
                ),
                headlineContent = {
                    Text(
                        text = stringResource(R.string.navigate_profile_option),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = null,
                    )
                }
            )
        }
    }
}