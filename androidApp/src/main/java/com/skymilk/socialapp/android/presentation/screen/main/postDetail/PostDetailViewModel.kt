package com.skymilk.socialapp.android.presentation.screen.main.postDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.state.PostState
import com.skymilk.socialapp.domain.model.PostComment
import com.skymilk.socialapp.domain.usecase.post.PostUseCase
import com.skymilk.socialapp.domain.usecase.postComments.PostCommentsUseCase
import com.skymilk.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val postUseCase: PostUseCase,
    private val postCommentUseCase: PostCommentsUseCase,
    private val postId: Long
) : ViewModel() {

    //선택한 게시글 상태
    private val _postState = MutableStateFlow<PostState>(PostState.Initial)
    val postState = _postState.asStateFlow()

    //선택한 게시글의 댓글 목록
    private val _postComments = MutableStateFlow<PagingData<PostComment>>(PagingData.empty())
    val postComments = _postComments.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: PostDetailEvent) {
        when (event) {
            is PostDetailEvent.RetryData -> {
                loadData()
            }

            else -> {}
        }
    }

    private fun loadData() {
        //게시물 정보 요청
        viewModelScope.launch {
            _postState.update { PostState.Loading }

            val result = postUseCase.getPost(postId)
            when (result) {
                is Result.Success -> {
                    _postState.update {
                        PostState.Success(post = result.data)
                    }
                }

                is Result.Error -> {
                    _postState.update {
                        PostState.Error(message = result.message.toString())
                    }
                }
            }
        }

        //댓글 목록 요청
        viewModelScope.launch {
            val comments = postCommentUseCase.getPostComments(postId).cachedIn(viewModelScope)
            comments.collect {
                _postComments.value = it
            }
        }
    }

}