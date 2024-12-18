package com.skymilk.socialapp.presentation.screen.main.follows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skymilk.socialapp.domain.model.FollowsUser
import com.skymilk.socialapp.domain.usecase.follows.FollowsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FollowsViewModel(
    private val followsUseCase: FollowsUseCase,
    private val userId: Long,
    private val followsType: Int,
) : ViewModel() {
    //팔로우 유저 목록
    private val _follows = MutableStateFlow<PagingData<FollowsUser>>(PagingData.empty())
    val follows = _follows.asStateFlow()

    init {
        loadFollows()
    }

    private fun loadFollows() {
        viewModelScope.launch {
            val follows = followsUseCase.getMyFollows(userId, followsType).cachedIn(viewModelScope)

            follows.collect {
                _follows.value = it
            }
        }
    }

}