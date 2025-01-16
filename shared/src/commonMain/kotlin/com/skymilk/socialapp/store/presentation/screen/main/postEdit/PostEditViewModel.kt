package com.skymilk.socialapp.store.presentation.screen.main.postEdit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.store.data.util.Result
import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.domain.usecase.post.PostUseCase
import com.skymilk.socialapp.store.presentation.screen.main.postEdit.state.PostEditUiState
import com.skymilk.socialapp.store.presentation.util.DataEvent
import com.skymilk.socialapp.store.presentation.util.MessageEvent
import com.skymilk.socialapp.store.presentation.util.sendEvent
import kotlinx.coroutines.launch

class PostEditViewModel(
    private val postUseCase: PostUseCase,
    private val post: Post
) : ViewModel() {

    var uiState by mutableStateOf(PostEditUiState())
        private set

    init {
        uiState = uiState.copy(
            caption = post.caption,
            images = post.imageUrls.map { PostImage.UrlImage(it) },
            maxSelection = uiState.maxSelection - post.imageUrls.size
        )
    }

    fun onEvent(event: PostEditEvent) {
        when (event) {
            is PostEditEvent.UpdateCaption -> uiState = uiState.copy(caption = event.caption)

            //이미지 리스트 통합
            is PostEditEvent.AddImage -> uiState =
                uiState.copy(
                    images = uiState.images + event.images.map { PostImage.ByteImage(it) },
                    maxSelection = uiState.maxSelection - event.images.size
                )

            //이미지 삭제
            is PostEditEvent.RemoveImage -> uiState = uiState.copy(
                images = uiState.images.filterIndexed { index, _ -> index != event.index },
                maxSelection = uiState.maxSelection + 1
            )

            is PostEditEvent.EditPost -> editPost()
        }
    }

    private fun editPost() {
        uiState = uiState.copy(isLoading = true)

        viewModelScope.launch {
            val result = postUseCase.updatePost(
                post.copy(
                    caption = uiState.caption,
                    imageUrls = uiState.images.filterIsInstance<PostImage.UrlImage>().map { it.url }
                ),
                uiState.images.filterIsInstance<PostImage.ByteImage>().map { it.byteArray }
            )

            when (result) {
                is Result.Success -> {
                    //게시글 업로드 완료 처리
                    uiState = uiState.copy(isUpdated = true)
                    sendEvent(DataEvent.UpdatedPost(result.data))

                    sendEvent(MessageEvent.SnackBar("게시글을 수정하였습니다."))
                }

                is Result.Error -> {
                    sendEvent(MessageEvent.SnackBar(result.message ?: "게시글 수정 오류가 발생하였습니다."))
                }
            }


            uiState = uiState.copy(isLoading = false)
        }
    }
}