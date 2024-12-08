package com.skymilk.socialapp.android.presentation.screen.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.skymilk.socialapp.android.presentation.screen.main.home.state.OnBoardingState
import com.skymilk.socialapp.domain.model.FollowsUser
import com.skymilk.socialapp.domain.model.Post
import com.skymilk.socialapp.domain.usecase.follows.FollowsUseCase
import com.skymilk.socialapp.domain.usecase.post.PostUseCase
import com.skymilk.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val followsUseCase: FollowsUseCase,
    private val postUseCase: PostUseCase,
) : ViewModel() {

    //상단 추천 유저 목록 상태
    private val _onBoardingState = MutableStateFlow<OnBoardingState>(OnBoardingState.Initial)
    val onBoardingUiState = _onBoardingState.asStateFlow()

    //메인 피드 목록 상태
    private val _feedPosts = MutableStateFlow<PagingData<Post>>(PagingData.empty())
    val feedPosts = _feedPosts.asStateFlow()

    init {
        loadData()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.DismissOnBoarding -> dismissOnBoarding()

            is HomeEvent.RetryData -> loadData()

            is HomeEvent.FollowUser -> followUser(event.user)

            is HomeEvent.LikePost -> likeOrDislikePost(event.post)
        }
    }

    private fun loadData() {
        _onBoardingState.update { OnBoardingState.Loading }

        //추천 유저 목록 불러오기
        viewModelScope.launch {
            val result = followsUseCase.getFollowableUsers()
            when (result) {
                is Result.Success -> {
                    _onBoardingState.update {
                        OnBoardingState.Success(
                            users = result.data,
                            shouldShowOnBoarding = result.data.isNotEmpty()
                        )
                    }
                }

                is Result.Error -> Unit
            }
        }

        //게시글 목록 불러오기
        viewModelScope.launch {
            val feed = postUseCase.getFeedPosts().cachedIn(viewModelScope)
            feed.collect {
                _feedPosts.value = it
            }
        }

    }

    private fun dismissOnBoarding() {
        //데이터가 로드 된 Success 상태일때 숨김 처리한다
        _onBoardingState.update {
            OnBoardingState.Dismiss
        }
    }

    private fun followUser(followsUser: FollowsUser) {
        viewModelScope.launch {
            val result = followsUseCase.followOrUnFollowUseCase(
                followedUserId = followsUser.id,
                shouldFollow = !followsUser.isFollowing
            )

            when (result) {
                is Result.Success -> {
                    _onBoardingState.update {
                        (it as? OnBoardingState.Success)?.copy(
                            //팔로윙 처리된 유저에 대해서 상태 변경
                            users = it.users.map { user ->
                                if (user.id == followsUser.id) user.copy(isFollowing = !followsUser.isFollowing)
                                else user
                            }
                        ) ?: it
                    }
                }

                is Result.Error -> {}
            }
        }
    }

    private fun likeOrDislikePost(currentPost: Post) {
        viewModelScope.launch {
            val count = if (currentPost.isLiked) -1 else 1
            val result = postUseCase.likeOrDislikePost(currentPost)

            when (result) {
                is Result.Success -> {
                    _feedPosts.value.map {
                        if (it.postId == currentPost.postId) it.copy(
                            isLiked = !it.isLiked,
                            likesCount = it.likesCount.plus(count)
                        )
                        else it
                    }
                }

                is Result.Error -> {}
            }
        }
    }
}