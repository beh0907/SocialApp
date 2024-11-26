package com.skymilk.socialapp.android.presentation.screen.main.postDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.android.presentation.common.dummy.sampleComments
import com.skymilk.socialapp.android.presentation.common.dummy.samplePosts
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.state.CommentsUiState
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.state.PostUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val postId: String
): ViewModel() {

    var postUiState by mutableStateOf(PostUiState())
        private set

    var commentsUiState by mutableStateOf(CommentsUiState())
        private set

    init {
        loadData()
    }

    fun onEvent(event: PostDetailEvent) {
        when(event) {
            is PostDetailEvent.RetryData -> {
                loadData()
            }
            else -> {}
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            postUiState = postUiState.copy(isLoading = true)

            delay(500)

            postUiState = postUiState.copy(
                isLoading = false,
                post = samplePosts.find { it.id == postId }
            )
        }

        viewModelScope.launch {
            commentsUiState = commentsUiState.copy(isLoading = true)

            delay(500)

            commentsUiState = commentsUiState.copy(
                isLoading = false,
                comments = sampleComments,
            )
        }
    }

}