package com.skymilk.socialapp.store.presentation.screen.main.postCreate.state

data class PostCreateUiState(
    val caption: String = "",
    val selectedImages: List<ByteArray> = emptyList(),
    val maxSelection:Int = 10,
    val isLoading: Boolean = false,
    val isCreated: Boolean = false
)