package com.skymilk.socialapp.domain.usecase.post

import com.skymilk.socialapp.domain.model.Post
import com.skymilk.socialapp.domain.repository.PostRepository
import com.skymilk.socialapp.util.Result

class GetFeedPosts(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(page: Int, pageSize: Int): Result<List<Post>> {
        return postRepository.getFeedPosts(page, pageSize)
    }
}