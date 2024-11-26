package com.skymilk.socialapp.android.presentation.screen.main.profile.state

import com.skymilk.socialapp.android.presentation.common.dummy.Post

data class UserPostsUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val errorMessages: String? = null,
)