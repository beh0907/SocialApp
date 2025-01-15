package com.skymilk.socialapp.store.presentation.screen.main.postCreate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.store.data.util.Result
import com.skymilk.socialapp.store.domain.usecase.post.PostUseCase
import com.skymilk.socialapp.store.presentation.screen.main.postCreate.state.PostCreateUiState
import com.skymilk.socialapp.store.presentation.util.DataEvent
import com.skymilk.socialapp.store.presentation.util.MessageEvent
import com.skymilk.socialapp.store.presentation.util.sendEvent
import kotlinx.coroutines.launch

class PostCreateViewModel(
    private val postUseCase: PostUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(PostCreateUiState())
        private set

    fun onEvent(event: PostCreateEvent) {
        when (event) {
            is PostCreateEvent.UpdateCaption -> uiState = uiState.copy(caption = event.caption)

            is PostCreateEvent.AddImage -> uiState =
                uiState.copy(
                    selectedImages = uiState.selectedImages + event.images,
                    maxSelection = uiState.maxSelection - event.images.size
                )

            is PostCreateEvent.RemoveImage -> uiState =
                uiState.copy(
                    selectedImages = uiState.selectedImages.filterIndexed { index, _ -> index != event.index },
                    maxSelection = uiState.maxSelection + 1
                )

            is PostCreateEvent.CreatePost -> createPost()
        }
    }

    private fun createPost() {
        uiState = uiState.copy(isLoading = true)

        viewModelScope.launch {
            val result = postUseCase.createPost(uiState.caption, uiState.selectedImages)

            when (result) {
                is Result.Success -> {
                    //게시글 업로드 완료 처리
                    uiState = uiState.copy(isCreated = true)
                    sendEvent(DataEvent.CreatedPost(result.data))

                    sendEvent(MessageEvent.SnackBar("게시글을 등록하였습니다."))
                }

                is Result.Error -> {
                    sendEvent(MessageEvent.SnackBar(result.message ?: "게시글 생성 오류가 발생하였습니다."))
                }
            }


            uiState = uiState.copy(isLoading = false)
        }
    }
}