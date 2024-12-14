package com.skymilk.socialapp.android.presentation.screen.main.postCreate

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.android.presentation.screen.main.postCreate.state.PostCreateUiState
import com.skymilk.socialapp.android.presentation.util.ImageBytesReader
import com.skymilk.socialapp.android.presentation.util.MessageEvent
import com.skymilk.socialapp.android.presentation.util.DataEvent
import com.skymilk.socialapp.android.presentation.util.sendEvent
import com.skymilk.socialapp.data.util.Result
import com.skymilk.socialapp.domain.usecase.post.PostUseCase
import kotlinx.coroutines.launch

class PostCreateViewModel(
    private val postUseCase: PostUseCase,
    private val imageBytesReader: ImageBytesReader
) : ViewModel() {

    var uiState by mutableStateOf(PostCreateUiState())
        private set

    fun onEvent(event: PostCreateEvent) {
        when (event) {
            is PostCreateEvent.UpdateCaption -> uiState = uiState.copy(caption = event.caption)

            is PostCreateEvent.UpdateImage -> updateImage(event.imageUri)

            is PostCreateEvent.CreatePost -> createPost()
        }
    }

    private fun updateImage(uri: Uri) {
        viewModelScope.launch {
            val result = imageBytesReader.readImageBytes(uri)

            when(result) {
                is Result.Success -> {
                    uiState = uiState.copy(imageBytes = result.data)
                }

                is Result.Error -> {
                    sendEvent(MessageEvent.Toast(result.message ?: "이미지 읽기 오류가 발생하였습니다."))
                }
            }
        }
    }

    private fun createPost() {
        uiState = uiState.copy(isLoading = true)

        viewModelScope.launch {
            val result = postUseCase.createPost(uiState.caption, uiState.imageBytes)

            when(result) {
                is Result.Success -> {
                    //게시글 업로드 완료 처리
                    uiState = uiState.copy(isCreated = true)
                    sendEvent(DataEvent.CreatedPost(result.data))

                    sendEvent(MessageEvent.Toast("게시글을 등록하였습니다."))
                }

                is Result.Error -> {
                    sendEvent(MessageEvent.Toast(result.message ?: "게시글 생성 오류가 발생하였습니다."))
                }
            }


            uiState = uiState.copy(isLoading = false)
        }
    }
}