package com.skymilk.socialapp.store.presentation.screen.main.postCreate.state

data class PostCreateUiState(
    val caption: String = "",
    val imageBytes: ByteArray? = null,
    val isLoading: Boolean = false,
    val isCreated: Boolean = false
)