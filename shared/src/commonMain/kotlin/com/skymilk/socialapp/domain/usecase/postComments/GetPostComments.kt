package com.skymilk.socialapp.domain.usecase.postComments

import app.cash.paging.PagingData
import com.skymilk.socialapp.domain.model.PostComment
import com.skymilk.socialapp.domain.repository.PostCommentsRepository
import kotlinx.coroutines.flow.Flow

class GetPostComments(
    private val postCommentsRepository: PostCommentsRepository
) {
    operator fun invoke(postId: Long): Flow<PagingData<PostComment>> {
        return postCommentsRepository.getPostComments(postId)
    }
}