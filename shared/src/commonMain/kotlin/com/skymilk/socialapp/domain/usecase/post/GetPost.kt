package com.skymilk.socialapp.domain.usecase.post

import com.skymilk.socialapp.domain.model.Post
import com.skymilk.socialapp.domain.repository.PostRepository
import com.skymilk.socialapp.util.Result

class GetPost(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: Long): Result<Post> {
        return postRepository.getPost(postId)
    }
}