package com.skymilk.socialapp.store.domain.usecase.post

import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.domain.repository.PostRepository
import com.skymilk.socialapp.store.data.util.Result

class GetPost(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: Long): Result<Post> {
        return postRepository.getPost(postId)
    }
}