package com.skymilk.socialapp.store.presentation.screen.main.postEdit

sealed interface PostEditEvent {

    data class UpdateCaption(val caption: String) : PostEditEvent

    data class UpdateImage(val byteArray: ByteArray) : PostEditEvent

    data object EditPost : PostEditEvent
}