package com.skymilk.socialapp.store.presentation.screen.main.postEdit.state

data class PostEditUiState(
    val caption: String = "",
    val imageUrls: List<String> = listOf(), // 기본 이미지 URL
    val imageBytes: ByteArray? = null, // 선택한 이미지는 ByteArray
    val isLoading: Boolean = false,
    val isUpdated: Boolean = false
)