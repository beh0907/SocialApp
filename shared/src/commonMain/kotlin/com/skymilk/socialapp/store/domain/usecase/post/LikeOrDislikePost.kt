package com.skymilk.socialapp.store.domain.usecase.post

import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.domain.repository.PostRepository
import com.skymilk.socialapp.store.data.util.Result

class LikeOrDislikePost(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(post: Post): Result<Boolean> {
        return postRepository.likeOrDislikePost(post.postId, !post.isLiked)
    }
}