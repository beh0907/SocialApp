package com.skymilk.socialapp.store.presentation.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.store.presentation.util.EventBus.sendMessageEvent
import com.skymilk.socialapp.store.presentation.util.EventBus.sendPostEvent
import kotlinx.coroutines.launch

//뷰모델 이벤트 전달
fun ViewModel.sendEvent(event: Any) {
    viewModelScope.launch {
        when (event) {
            is MessageEvent -> sendMessageEvent(event)
            is DataEvent -> sendPostEvent(event)
        }
    }
}