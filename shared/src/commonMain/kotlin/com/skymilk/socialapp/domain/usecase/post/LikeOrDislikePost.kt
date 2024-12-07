package com.skymilk.socialapp.domain.usecase.post

import com.skymilk.socialapp.domain.model.FollowsUser
import com.skymilk.socialapp.domain.model.Post
import com.skymilk.socialapp.domain.repository.PostRepository
import com.skymilk.socialapp.util.Result

class LikeOrDislikePost(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(post: Post): Result<Boolean> {
        return postRepository.likeOrDislikePost(post.id, !post.isLiked)
    }
}