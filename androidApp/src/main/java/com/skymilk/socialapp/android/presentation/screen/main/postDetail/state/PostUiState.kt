package com.skymilk.socialapp.android.presentation.screen.main.postDetail.state

import com.skymilk.socialapp.android.presentation.common.dummy.Post

data class PostUiState(
    val isLoading: Boolean = false,
    val post: Post? = null,
    val errorMessages: String? = null,
)