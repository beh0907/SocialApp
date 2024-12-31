package com.skymilk.socialapp.store.domain.usecase.post

data class PostUseCase(
    val getPost:GetPost,
    val getFeedPosts: GetFeedPosts,
    val getUserPosts: GetUserPosts,
    val likeOrDislikePost: LikeOrDislikePost,
    val createPost: CreatePost,
    val removePost: RemovePost
)
