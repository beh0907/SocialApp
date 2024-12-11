package com.skymilk.socialapp.domain.usecase.postComments

import com.skymilk.socialapp.domain.model.PostComment
import com.skymilk.socialapp.domain.repository.PostCommentsRepository
import com.skymilk.socialapp.data.util.Result

class RemovePostComment(
    private val postCommentsRepository: PostCommentsRepository
) {
    suspend operator fun invoke(postId: Long, commentId: Long): Result<PostComment> {
        return postCommentsRepository.removePostComment(postId, commentId)
    }
}