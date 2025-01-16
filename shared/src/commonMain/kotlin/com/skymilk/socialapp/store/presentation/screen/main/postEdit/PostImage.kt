package com.skymilk.socialapp.store.presentation.screen.main.postEdit

sealed class PostImage {
    data class UrlImage(val url: String) : PostImage()
    data class ByteImage(val byteArray: ByteArray) : PostImage()
}