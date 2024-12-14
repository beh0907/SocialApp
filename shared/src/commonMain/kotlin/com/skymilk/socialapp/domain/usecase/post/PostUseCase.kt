package com.skymilk.socialapp.domain.usecase.post

data class PostUseCase(
    val getPost:GetPost,
    val getFeedPosts: GetFeedPosts,
    val getUserPosts: GetUserPosts,
    val likeOrDislikePost: LikeOrDislikePost,
    val createPost: CreatePost
)
