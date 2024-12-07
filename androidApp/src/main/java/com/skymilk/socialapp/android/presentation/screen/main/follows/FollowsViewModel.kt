package com.skymilk.socialapp.android.presentation.screen.main.follows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymilk.socialapp.android.presentation.common.dummy.sampleFollowsUser
import com.skymilk.socialapp.android.presentation.screen.main.follows.state.FollowsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FollowsViewModel(
    private val userId: Long,
    private val followsType: Int,
) : ViewModel() {

    private val _followsState = MutableStateFlow<FollowsState>(FollowsState.Initial)
    val followsUsersState = _followsState.asStateFlow()

    init {
        loadFollows()
    }

    fun onEvent(event: FollowsEvent) {
        when (event) {
            else -> {}
        }
    }

    private fun loadFollows() {
        viewModelScope.launch {
            _followsState.update { FollowsState.Loading }

            delay(1000)

            _followsState.update { FollowsState.Success(followsUsers = sampleFollowsUser) }
        }
    }

}