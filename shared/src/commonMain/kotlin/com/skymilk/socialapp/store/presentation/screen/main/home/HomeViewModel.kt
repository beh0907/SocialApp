package com.skymilk.socialapp.store.presentation.screen.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertHeaderItem
import androidx.paging.map
import com.skymilk.socialapp.store.data.util.Result
import com.skymilk.socialapp.store.domain.model.FollowsUser
import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.domain.model.Profile
import com.skymilk.socialapp.store.domain.usecase.follows.FollowsUseCase
import com.skymilk.socialapp.store.domain.usecase.post.PostUseCase
import com.skymilk.socialapp.store.presentation.screen.main.home.state.OnBoardingState
import com.skymilk.socialapp.store.presentation.util.DataEvent
import com.skymilk.socialapp.store.presentation.util.EventBus.dataEvents
import com.skymilk.socialapp.store.presentation.util.MessageEvent
import com.skymilk.socialapp.store.presentation.util.sendEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

        onUpdatedEvent()
    }

    //다른 화면에서 업데이트된 정보 반영
    private fun onUpdatedEvent() {
        dataEvents.onEach {
            when (it) {
                is DataEvent.CreatedPost -> {
                    _feedPosts.value.insertHeaderItem(item = it.post)
                }

                is DataEvent.UpdatedPost -> updatePost(it.post)

                is DataEvent.UpdatedProfile -> updateProfile(it.profile)
            }
        }.launchIn(viewModelScope)
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
            when (val result = followsUseCase.getFollowableUsers()) {
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
            val feeds = postUseCase.getFeedPosts().cachedIn(viewModelScope)
            feeds.collect {
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

                is Result.Error -> {
                    sendEvent(MessageEvent.SnackBar(result.message ?: "팔로우 처리에 실패하였습니다."))
                }
            }
        }
    }

    //좋아요 처리
    private fun likeOrDislikePost(currentPost: Post) {
        viewModelScope.launch {
            val count = if (currentPost.isLiked) -1 else 1
            val updatedPost = currentPost.copy(
                isLiked = !currentPost.isLiked,
                likesCount = currentPost.likesCount.plus(count)
            )

            val result = postUseCase.likeOrDislikePost(currentPost)

            when (result) {
                is Result.Success -> {
                    //메인 화면에 있는 일치한 게시글을 찾아 상태를 반영한다
                    updatePost(updatedPost)
                }

                is Result.Error -> {
                    sendEvent(MessageEvent.SnackBar(result.message ?: "좋아요 처리에 실패하였습니다."))
                }
            }
        }
    }

    //메인 화면 게시글에 일치한 것을 찾아 갱신한다
    private fun updatePost(post: Post) {
        _feedPosts.value = _feedPosts.value.map {
            if (it.postId == post.postId) post
            else it
        }
    }

    //메인 화면 게시글에 수정된 내 정보와 일치한 것을 찾아 갱신한다
    private fun updateProfile(profile: Profile) {
        _feedPosts.value = _feedPosts.value.map {
            if (it.userId == profile.id) it.copy(
                userName = profile.name,
                userImageUrl = profile.imageUrl
            )
            else it
        }
    }
}