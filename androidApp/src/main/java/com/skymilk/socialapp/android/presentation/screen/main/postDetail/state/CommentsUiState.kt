package com.skymilk.socialapp.android.presentation.screen.main.postDetail.state

import com.skymilk.socialapp.android.presentation.common.dummy.Comment

data class CommentsUiState(
    val isLoading: Boolean = false,
    val comments: List<Comment> = emptyList(),
    val errorMessages: String? = null,
)