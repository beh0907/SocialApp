package com.skymilk.socialapp.domain.repository

import com.skymilk.socialapp.domain.model.Post
import com.skymilk.socialapp.util.Result

interface PostRepository {
    //게시물 목록 요청
    suspend fun getFeedPosts(page: Int, pageSize: Int): Result<List<Post>>

    //좋아요 / 좋아요 해제 요청
    suspend fun likeOrDislikePost(postId: Long, shouldLike: Boolean): Result<Boolean>

    //유저 게시물 목록 요청
    suspend fun getUserPosts(userId: Long, page: Int, pageSize: Int): Result<List<Post>>

    //선택한 게시물 상세 정보 요청
    suspend fun getPost(postId: Long): Result<Post>

    //게시물 작성
    suspend fun createPost(text: String, imageBytes: ByteArray): Result<Post>
}