package com.skymilk.chatapp.store.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.android.store.presentation.util.EventBus
import kotlinx.coroutines.launch

//뷰모델 이벤트 전달
fun ViewModel.sendEvent(event: Any) {
    viewModelScope.launch {
        EventBus.sendEvent(event)
    }
}