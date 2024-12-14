package com.skymilk.socialapp.android.presentation.screen.main.postCreate

import android.net.Uri

sealed interface PostCreateEvent {

    data class UpdateCaption(val caption: String) : PostCreateEvent

    data class UpdateImage(val imageUri: Uri = Uri.EMPTY) : PostCreateEvent

    data object CreatePost : PostCreateEvent
}