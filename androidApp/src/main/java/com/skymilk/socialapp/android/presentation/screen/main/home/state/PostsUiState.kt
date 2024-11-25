package com.skymilk.socialapp.android.presentation.screen.main.home.state

import com.skymilk.socialapp.android.presentation.common.dummy.Post

data class PostsUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val errorMessage: String? = null,
)