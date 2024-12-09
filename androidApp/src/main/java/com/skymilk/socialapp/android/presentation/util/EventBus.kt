package com.skymilk.socialapp.android.presentation.util

import com.skymilk.socialapp.domain.model.Post
import com.skymilk.socialapp.util.Constants
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object EventBus {
    private val _messageEvents =
        MutableSharedFlow<MessageEvent>(extraBufferCapacity = Constants.EVENT_BUS_BUFFER_CAPACITY)
    val messageEvents = _messageEvents.asSharedFlow()

    private val _postEvents =
        MutableSharedFlow<PostEvent>(extraBufferCapacity = Constants.EVENT_BUS_BUFFER_CAPACITY)
    val postEvents = _postEvents.asSharedFlow()

    suspend fun sendMessageEvent(event: MessageEvent) {
        _messageEvents.emit(event)
    }

    suspend fun sendPostEvent(event: PostEvent) {
        _postEvents.emit(event)
    }
}

//알람 이벤트 정의
sealed interface MessageEvent {
    data class Toast(val message: String) : MessageEvent
    data class SnackBar(val message: String) : MessageEvent
}

//게시글 이벤트 정의
sealed interface PostEvent {
    data class UpdatedPost(val post: Post) : PostEvent
}