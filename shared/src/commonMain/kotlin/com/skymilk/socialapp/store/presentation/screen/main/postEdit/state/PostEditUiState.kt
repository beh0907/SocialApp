package com.skymilk.socialapp.store.presentation.screen.main.postEdit.state

import com.skymilk.socialapp.store.presentation.screen.main.postEdit.PostImage

data class PostEditUiState(
    val caption: String = "",
    val images: List<PostImage> = emptyList(), // 기본 이미지 URL과 선택한 이미지 ByteArray 통합
    val maxSelection: Int = 10,
    val isLoading: Boolean = false,
    val isUpdated: Boolean = false
)