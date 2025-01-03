package com.skymilk.socialapp.store.domain.repository

import app.cash.paging.PagingData
import com.skymilk.socialapp.store.data.util.Result
import com.skymilk.socialapp.store.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    //게시글 목록 요청
    fun getFeedPosts(): Flow<PagingData<Post>>

    //유저 게시글 목록 요청
    fun getUserPosts(userId: Long): Flow<PagingData<Post>>

    //좋아요 / 좋아요 해제 요청
    suspend fun likeOrDislikePost(postId: Long, shouldLike: Boolean): Result<Boolean>

    //선택한 게시글 상세 정보 요청
    suspend fun getPost(postId: Long): Result<Post>

    //게시글 작성
    suspend fun createPost(caption: String, imageBytes: ByteArray): Result<Post>

    //게시글 수정
    suspend fun updatePost(post: Post, imageBytes: ByteArray?): Result<Post>

    //게시글 삭제
    suspend fun removePost(postId: Long): Result<Post>
}