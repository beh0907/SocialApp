package com.skymilk.socialapp.store.presentation.screen.main.postCreate

sealed interface PostCreateEvent {

    data class UpdateCaption(val caption: String) : PostCreateEvent

    data class AddImage(val images: List<ByteArray>) : PostCreateEvent

    data class RemoveImage(val index: Int) : PostCreateEvent

    data object CreatePost : PostCreateEvent
}