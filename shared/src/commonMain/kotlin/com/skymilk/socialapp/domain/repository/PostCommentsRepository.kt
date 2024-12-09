package com.skymilk.socialapp.domain.repository

import app.cash.paging.PagingData
import com.skymilk.socialapp.domain.model.Post
import com.skymilk.socialapp.domain.model.PostComment
import com.skymilk.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface PostCommentsRepository {
    //게시글 댓글 목록 요청
    fun getPostComments(postId: Long): Flow<PagingData<PostComment>>

    //댓글 작성
    suspend fun addPostComment(postId: Long, content: String): Result<PostComment>

    //댓글 삭제
    suspend fun removePostComment(postId: Long, commentId: Long): Result<PostComment>
}