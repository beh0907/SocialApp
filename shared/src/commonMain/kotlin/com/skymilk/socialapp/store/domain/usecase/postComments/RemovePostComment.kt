package com.skymilk.socialapp.store.domain.usecase.postComments

import com.skymilk.socialapp.store.domain.repository.PostCommentsRepository
import com.skymilk.socialapp.store.data.util.Result

class RemovePostComment(
    private val postCommentsRepository: PostCommentsRepository
) {
    suspend operator fun invoke(postId: Long, commentId: Long): Result<Boolean> {
        return postCommentsRepository.removePostComment(postId, commentId)
    }
}