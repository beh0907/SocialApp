package com.skymilk.socialapp.domain.usecase.post

import com.skymilk.socialapp.domain.model.Post
import com.skymilk.socialapp.domain.repository.PostRepository
import com.skymilk.socialapp.data.util.Result

class LikeOrDislikePost(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(post: Post): Result<Boolean> {
        return postRepository.likeOrDislikePost(post.postId, !post.isLiked)
    }
}