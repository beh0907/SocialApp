package com.skymilk.socialapp.store.presentation.screen.main.postDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.skymilk.socialapp.store.data.util.Result
import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.domain.model.PostComment
import com.skymilk.socialapp.store.domain.model.Profile
import com.skymilk.socialapp.store.domain.usecase.post.PostUseCase
import com.skymilk.socialapp.store.domain.usecase.postComments.PostCommentsUseCase
import com.skymilk.socialapp.store.presentation.screen.main.postDetail.state.PostDetailState
import com.skymilk.socialapp.store.presentation.screen.main.postDetail.state.PostDetailUiState
import com.skymilk.socialapp.store.presentation.util.DataEvent
import com.skymilk.socialapp.store.presentation.util.EventBus.dataEvents
import com.skymilk.socialapp.store.presentation.util.MessageEvent
import com.skymilk.socialapp.store.presentation.util.sendEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val postUseCase: PostUseCase,
    private val postCommentUseCase: PostCommentsUseCase,
    private val postId: Long
) : ViewModel() {

    //선택한 게시글 상태
    private val _postDetailState = MutableStateFlow<PostDetailState>(PostDetailState.Initial)
    val postState = _postDetailState.asStateFlow()

    //선택한 게시글의 댓글 목록
    private val _postComments = MutableStateFlow<PagingData<PostComment>>(PagingData.empty())
    val postComments = _postComments.asStateFlow()

    //UI 상태 정보
    var postDetailUiState by mutableStateOf(PostDetailUiState())
        private set

    init {
        loadPostDetail()
        loadComments()

        onUpdatedEvent()
    }

    //다른 화면에서 업데이트된 정보 반영
    private fun onUpdatedEvent() {
        dataEvents.onEach {
            when (it) {
                is DataEvent.CreatedPost -> Unit

                is DataEvent.RemovedPost -> Unit

                is DataEvent.UpdatedPost -> updatePost(it.post)

                is DataEvent.UpdatedProfile -> updateProfile(it.profile)
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: PostDetailEvent) {
        when (event) {
            is PostDetailEvent.SetSelectedPostOption -> postDetailUiState = postDetailUiState.copy(isSelectedPostOption = event.isSelected)

            is PostDetailEvent.SetShowRemoveDialog -> postDetailUiState = postDetailUiState.copy(isShowRemoveDialog = event.isShow)

            is PostDetailEvent.SetSelectedPostComment -> postDetailUiState = postDetailUiState.copy(selectedPostComment = event.postComment)

            is PostDetailEvent.LikePost -> likeOrDislikePost(event.post)

            is PostDetailEvent.RemovePost -> removePost(event.post)

            is PostDetailEvent.ChangeComment -> postDetailUiState = postDetailUiState.copy(comment = event.comment)

            is PostDetailEvent.RemoveComment -> removeComment(event.comment)

            is PostDetailEvent.SendComment -> sendComment()
        }
    }

    //게시글 정보 요청
    private fun loadPostDetail() {
        viewModelScope.launch {
            _postDetailState.update { PostDetailState.Loading }

            val result = postUseCase.getPost(postId)
            when (result) {
                is Result.Success -> {
                    _postDetailState.update {
                        PostDetailState.Success(post = result.data)
                    }
                }

                is Result.Error -> {
                    _postDetailState.update {
                        PostDetailState.Error(message = result.message.toString())
                    }
                }
            }
        }
    }

    //댓글 목록 요청
    private fun loadComments() {
        viewModelScope.launch {
            val comments = postCommentUseCase.getPostComments(postId).cachedIn(viewModelScope)
            comments.collect {
                _postComments.value = it
            }
        }
    }

    //좋아요 처리
    private fun likeOrDislikePost(currentPost: Post) {
        viewModelScope.launch {
            val count = if (currentPost.isLiked) -1 else 1
            val updatedPost = currentPost.copy(
                isLiked = !currentPost.isLiked,
                likesCount = currentPost.likesCount.plus(count)
            )

            val result = postUseCase.likeOrDislikePost(currentPost)
            when (result) {
                is Result.Success -> {
                    //메인 화면에 있는 일치한 게시글을 찾아 상태를 반영한다
                    sendEvent(DataEvent.UpdatedPost(updatedPost))
                }

                is Result.Error -> {
                    // 오류 알림
                    sendEvent(MessageEvent.SnackBar("좋아요 처리를 실패했습니다."))
                }
            }
        }
    }

    //게시글 삭제
    private fun removePost(post: Post) {
        viewModelScope.launch {
            val result = postUseCase.removePost(
                postId = post.postId,
            )

            when (result) {
                is Result.Success -> {
                    //게시글 삭제
                    sendEvent(DataEvent.RemovedPost(post))

                    //게시글 삭제 알림
                    sendEvent(MessageEvent.SnackBar("게시글이 삭제되었습니다."))

                    //게시글 삭제 처리 설정
                    postDetailUiState = postDetailUiState.copy(isDeletedPost = true)
                }

                is Result.Error -> {
                    // 오류 알림
                    sendEvent(MessageEvent.SnackBar("게시글 삭제를 실패했습니다."))
                }
            }
        }
    }

    //댓글 삭제
    private fun removeComment(comment: PostComment) {
        viewModelScope.launch {
            var post = (_postDetailState.value as? PostDetailState.Success)?.post ?: return@launch
            val result = postCommentUseCase.removePostComment(
                postId = comment.postId,
                commentId = comment.commentId
            )

            when (result) {
                is Result.Success -> {
                    // PagingData에서 해당 댓글 제거
                    _postComments.update { pagingData ->
                        pagingData.filter { existingComment ->
                            existingComment.commentId != comment.commentId
                        }
                    }

                    //게시글 상태 정보 갱신
                    post = post.copy(commentsCount = post.commentsCount.minus(1))
                    sendEvent(DataEvent.UpdatedPost(post))

                    //댓글 추가 알림
                    sendEvent(MessageEvent.SnackBar("댓글이 삭제되었습니다."))
                }

                is Result.Error -> {
                    // 오류 알림
                    sendEvent(MessageEvent.SnackBar("댓글 삭제를 실패했습니다."))
                }
            }
        }
    }

    //댓글 전송
    private fun sendComment() {
        viewModelScope.launch {
            var post = (_postDetailState.value as? PostDetailState.Success)?.post ?: return@launch
            val result = postCommentUseCase.addPostComment(
                postId = postId,
                content = postDetailUiState.comment
            )

            when (result) {
                is Result.Success -> {
                    //게시글 상태 정보 갱신
                    post = post.copy(commentsCount = post.commentsCount.plus(1))
                    sendEvent(DataEvent.UpdatedPost(post))

                    //댓글 추가 알림
                    sendEvent(MessageEvent.SnackBar("댓글이 추가되었습니다."))

                    //입력값 초기화
                    postDetailUiState = postDetailUiState.copy(comment = "")

                    //새 댓글 목록 다시 불러오기
                    loadComments()
                }

                is Result.Error -> {
                    // 오류 알림
                    sendEvent(MessageEvent.SnackBar("댓글 작성을 실패했습니다."))
                }
            }
        }
    }

    //상세 게시글 정보를 수정한다
    private fun updatePost(post: Post) {
        _postDetailState.update { PostDetailState.Success(post) }
    }

    private fun updateProfile(profile: Profile) {
        val post = (_postDetailState.value as? PostDetailState.Success)?.post ?: return

        //나의 게시글이라면 갱신
        if (post.isOwnPost) {
            val updatedPost = post.copy(
                userName = profile.name,
                userImageUrl = profile.imageUrl
            )
            updatePost(updatedPost)
        }

        _postComments.value.map {
            //내가 작성한 댓글에 대한 수정 정보 반영
            if (it.userId == profile.id) {
                it.copy(
                    userName = profile.name,
                    userImageUrl = profile.imageUrl
                )
            } else it
        }
    }
}