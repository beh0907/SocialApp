package com.skymilk.socialapp.store.domain.usecase.post

import app.cash.paging.PagingData
import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetUserPosts(
    private val postRepository: PostRepository
) {
    operator fun invoke(userId: Long): Flow<PagingData<Post>> {
        return postRepository.getUserPosts(userId)
    }
}