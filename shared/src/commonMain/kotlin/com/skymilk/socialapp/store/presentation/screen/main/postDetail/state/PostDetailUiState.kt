package com.skymilk.socialapp.store.presentation.screen.main.postDetail.state

import com.skymilk.socialapp.store.domain.model.PostComment

data class PostDetailUiState(
    val comment: String = "", // 댓글 입력
    val isSelectedPostOption: Boolean = false, // 게시글 옵션 선택
    val isShowRemoveDialog: Boolean = false, // 삭제 다이얼로그 팝업
    val selectedPostComment: PostComment? = null, // 댓글 옵션 선택
    val isDeletedPost: Boolean = false, // 포스트 삭제 여부 체크
)