package com.skymilk.socialapp.store.domain.usecase.post

import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.domain.repository.PostRepository
import com.skymilk.socialapp.store.data.util.Result
import com.skymilk.socialapp.util.Constants

class CreatePost(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(text: String, imageBytes: ByteArray?): Result<Post> {
        if (text.isBlank()) return Result.Error(Constants.INVALID_INPUT_CAPTION_MESSAGE)

        if (imageBytes == null) return Result.Error(Constants.INVALID_UPLOAD_POST_IMAGE_MESSAGE)

        return postRepository.createPost(text, imageBytes)
    }
}