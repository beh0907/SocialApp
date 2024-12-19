package com.skymilk.socialapp.store.domain.usecase.postComments

import com.skymilk.socialapp.store.domain.model.PostComment
import com.skymilk.socialapp.store.domain.repository.PostCommentsRepository
import com.skymilk.socialapp.store.data.util.Result

class AddPostComment(
    private val postCommentsRepository: PostCommentsRepository
) {
    suspend operator fun invoke(postId: Long, content: String): Result<PostComment> {
        return postCommentsRepository.addPostComment(postId, content)
    }
}