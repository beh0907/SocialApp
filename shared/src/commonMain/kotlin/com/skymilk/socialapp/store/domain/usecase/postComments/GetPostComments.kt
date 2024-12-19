package com.skymilk.socialapp.store.domain.usecase.postComments

import app.cash.paging.PagingData
import com.skymilk.socialapp.store.domain.model.PostComment
import com.skymilk.socialapp.store.domain.repository.PostCommentsRepository
import kotlinx.coroutines.flow.Flow

class GetPostComments(
    private val postCommentsRepository: PostCommentsRepository
) {
    operator fun invoke(postId: Long): Flow<PagingData<PostComment>> {
        return postCommentsRepository.getPostComments(postId)
    }
}