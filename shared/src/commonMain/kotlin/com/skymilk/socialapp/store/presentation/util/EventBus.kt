package com.skymilk.socialapp.store.presentation.util

import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.domain.model.Profile
import com.skymilk.socialapp.util.Constants
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object EventBus {
    private val _messageEvents =
        MutableSharedFlow<MessageEvent>(extraBufferCapacity = Constants.EVENT_BUS_BUFFER_CAPACITY)
    val messageEvents = _messageEvents.asSharedFlow()

    private val _dataEvents =
        MutableSharedFlow<DataEvent>(extraBufferCapacity = Constants.EVENT_BUS_BUFFER_CAPACITY)
    val dataEvents = _dataEvents.asSharedFlow()

    suspend fun sendMessageEvent(event: MessageEvent) {
        _messageEvents.emit(event)
    }

    suspend fun sendPostEvent(event: DataEvent) {
        _dataEvents.emit(event)
    }
}

//알람 이벤트 정의
sealed interface MessageEvent {
    data class SnackBar(val message: String, val action: (() -> Unit)? = null) : MessageEvent
}

//게시글 이벤트 정의
sealed interface DataEvent {
    data class CreatedPost(val post: Post) : DataEvent

    data class UpdatedPost(val post: Post) : DataEvent

    data class UpdatedProfile(val profile: Profile) : DataEvent
}