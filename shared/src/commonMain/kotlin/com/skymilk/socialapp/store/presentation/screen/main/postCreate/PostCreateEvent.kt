package com.skymilk.socialapp.store.presentation.screen.main.postCreate

sealed interface PostCreateEvent {

    data class UpdateCaption(val caption: String) : PostCreateEvent

    data class UpdateImage(val byteArray: ByteArray) : PostCreateEvent

    data object CreatePost : PostCreateEvent
}