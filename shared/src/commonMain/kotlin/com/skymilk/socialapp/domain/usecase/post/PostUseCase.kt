package com.skymilk.socialapp.domain.usecase.post

data class PostUseCase(
    val getFeedPosts: GetFeedPosts,
    val likeOrDislikePost: LikeOrDislikePost
)
