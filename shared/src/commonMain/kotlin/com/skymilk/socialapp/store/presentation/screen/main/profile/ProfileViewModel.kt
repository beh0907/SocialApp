package com.skymilk.socialapp.store.presentation.screen.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.skymilk.socialapp.store.data.util.Result
import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.domain.model.Profile
import com.skymilk.socialapp.store.domain.usecase.follows.FollowsUseCase
import com.skymilk.socialapp.store.domain.usecase.post.PostUseCase
import com.skymilk.socialapp.store.domain.usecase.profile.ProfileUseCase
import com.skymilk.socialapp.store.presentation.screen.main.profile.state.ProfileState
import com.skymilk.socialapp.store.presentation.util.DataEvent
import com.skymilk.socialapp.store.presentation.util.EventBus.dataEvents
import com.skymilk.socialapp.store.presentation.util.sendEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val followsUseCase: FollowsUseCase,
    private val profileUseCase: ProfileUseCase,
    private val postUseCase: PostUseCase,
    private val userId: Long
) : ViewModel() {
    //프로필 상태
    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Initial)
    val profileState = _profileState.asStateFlow()

    //선택한 유저의 게시글 목록
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
                is DataEvent.UpdatedProfile -> updateProfile(it.profile)

                else -> Unit
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.RetryData -> loadData()

            is ProfileEvent.FollowUser -> followUser(event.profile)

            is ProfileEvent.LikePost -> likeOrDislikePost(event.post)
        }
    }

    private fun loadData() {
        //프로필 정보 요청
        viewModelScope.launch {
            _profileState.update { ProfileState.Loading }

            profileUseCase.getProfile(userId).collect {
                _profileState.value = when (it) {
                    is Result.Success -> {
                        ProfileState.Success(profile = it.data)
                    }

                    is Result.Error -> {
                        ProfileState.Error(message = it.message.toString())
                    }
                }
            }
        }

        //선택한 유저의 게시글 목록
        viewModelScope.launch {
            val feed = postUseCase.getUserPosts(userId).cachedIn(viewModelScope)
            feed.collect {
                _feedPosts.value = it
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
                    _feedPosts.value.map {
                        if (it.postId == currentPost.postId) updatedPost
                        else it
                    }

                    //메인 화면에 있는 일치한 게시글을 찾아 상태를 반영한다
                    sendEvent(DataEvent.UpdatedPost(updatedPost))
                }

                is Result.Error -> {}
            }
        }
    }

    //팔로우 처리
    private fun followUser(profile: Profile) {
        val count = if (profile.isFollowing) -1 else 1
        viewModelScope.launch {
            val result = followsUseCase.followOrUnFollowUseCase(
                followedUserId = profile.id,
                shouldFollow = !profile.isFollowing
            )

            when (result) {
                is Result.Success -> {
                    _profileState.update {
                        (it as? ProfileState.Success)?.copy(
                            //팔로윙 처리된 유저에 대해서 상태 변경
                            profile = it.profile.copy(
                                isFollowing = !profile.isFollowing,
                                followersCount = profile.followersCount.plus(count)
                            )
                        ) ?: it
                    }
                }

                is Result.Error -> {}
            }
        }
    }


    //프로필 화면의 내 정보들을 갱신한다
    private fun updateProfile(profile: Profile) {
        //내 정보 수정
        _profileState.update { ProfileState.Success(profile) }

        //게시글 정보 수정
        _feedPosts.value = _feedPosts.value.map {
            if (it.userId == profile.id)
                it.copy(
                    userName = profile.name,
                    userImageUrl = profile.imageUrl
                )
            else it
        }
    }
}