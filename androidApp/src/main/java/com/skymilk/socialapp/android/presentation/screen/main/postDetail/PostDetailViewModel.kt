package com.skymilk.socialapp.android.presentation.screen.main.postDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.android.presentation.common.dummy.sampleComments
import com.skymilk.socialapp.android.presentation.common.dummy.samplePosts
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.state.CommentsState
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.state.PostState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val postId: Long
): ViewModel() {

    private val _postState = MutableStateFlow<PostState>(PostState.Initial)
    val postState = _postState.asStateFlow()

    private val _commentsState = MutableStateFlow<CommentsState>(CommentsState.Initial)
    val commentsState = _commentsState.asStateFlow()

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
            _postState.update { PostState.Loading }

            delay(500)

            _postState.update {
                val post = samplePosts.find { it.id == postId }

                if (post != null) PostState.Success(post = post)
                else PostState.Error("게시글을 찾을 수 없습니다.")
            }
        }

        viewModelScope.launch {
            _commentsState.update { CommentsState.Loading }

            delay(500)

            _commentsState.update {
                CommentsState.Success(comments = sampleComments)
            }
        }
    }

}