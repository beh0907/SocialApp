package com.skymilk.socialapp.store.domain.repository

import app.cash.paging.PagingData
import com.skymilk.socialapp.store.domain.model.PostComment
import com.skymilk.socialapp.store.data.util.Result
import kotlinx.coroutines.flow.Flow

interface PostCommentsRepository {
    //게시글 댓글 목록 요청
    fun getPostComments(postId: Long): Flow<PagingData<PostComment>>

    //댓글 작성
    suspend fun addPostComment(postId: Long, content: String): Result<PostComment>

    //댓글 삭제
    suspend fun removePostComment(postId: Long, commentId: Long): Result<Boolean>
}