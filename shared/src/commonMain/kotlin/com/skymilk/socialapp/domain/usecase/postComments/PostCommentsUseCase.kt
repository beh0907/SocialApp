package com.skymilk.socialapp.domain.usecase.postComments

data class PostCommentsUseCase(
    val getPostComments: GetPostComments,
    val addPostComment: AddPostComment,
    val removePostComment: RemovePostComment
)
