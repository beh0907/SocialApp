package com.skymilk.socialapp.store.domain.usecase.post

import com.skymilk.socialapp.store.data.util.Result
import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.domain.repository.PostRepository
import com.skymilk.socialapp.util.Constants

class UpdatePost(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(
        post:Post,
        imageBytes: ByteArray?
    ): Result<Post> {
        if (post.caption.isBlank()) return Result.Error(Constants.INVALID_INPUT_CAPTION_MESSAGE)

        return postRepository.updatePost(post, imageBytes)
    }
}