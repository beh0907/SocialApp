package com.skymilk.socialapp.domain.usecase.post

import app.cash.paging.PagingData
import com.skymilk.socialapp.domain.model.Post
import com.skymilk.socialapp.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetUserPosts(
    private val postRepository: PostRepository
) {
    operator fun invoke(userId: Long): Flow<PagingData<Post>> {
        return postRepository.getUserPosts(userId)
    }
}