package com.skymilk.socialapp.store.presentation.screen.main.postEdit

sealed interface PostEditEvent {

    data class UpdateCaption(val caption: String) : PostEditEvent

    data class AddImage(val images: List<ByteArray>) : PostEditEvent

    data class RemoveImage(val index: Int) : PostEditEvent

    data object EditPost : PostEditEvent
}