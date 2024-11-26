package com.skymilk.socialapp.android.presentation.screen.main.postDetail

sealed interface PostDetailEvent {

    data object RetryData : PostDetailEvent

}