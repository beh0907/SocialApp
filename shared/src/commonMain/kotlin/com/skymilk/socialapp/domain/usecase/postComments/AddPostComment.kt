package com.skymilk.socialapp.domain.usecase.postComments

import com.skymilk.socialapp.domain.model.PostComment
import com.skymilk.socialapp.domain.repository.PostCommentsRepository
import com.skymilk.socialapp.data.util.Result

class AddPostComment(
    private val postCommentsRepository: PostCommentsRepository
) {
    suspend operator fun invoke(postId: Long, content: String): Result<PostComment> {
        return postCommentsRepository.addPostComment(postId, content)
    }
}