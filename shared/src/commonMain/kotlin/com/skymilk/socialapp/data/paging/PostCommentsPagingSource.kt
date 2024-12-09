package com.skymilk.socialapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skymilk.socialapp.data.local.UserPreferences
import com.skymilk.socialapp.data.remote.PostCommentsApiService
import com.skymilk.socialapp.domain.model.PostComment
import com.skymilk.socialapp.util.Constants
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException
import kotlin.let

internal class PostCommentsPagingSource(
    private val postCommentsApiService: PostCommentsApiService,
    private val userPreferences: UserPreferences,
    private val postId: Long
) : PagingSource<Int, PostComment>() {
    override fun getRefreshKey(state: PagingState<Int, PostComment>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostComment> {
        //키 값이 없다면 다시 첫페이지부터 (0이 시작일 수도 있다)
        val currentPage = params.key ?: Constants.FIRST_PAGE_INDEX

        return try {
            val userData = userPreferences.getUserData()
            val response = postCommentsApiService.getPostComments(
                token = userData.token,
                postId = postId,
                page = currentPage,
                pageSize = params.loadSize
            )

            when (response.code) {
                HttpStatusCode.OK -> {
                    val posts = response.data.comments.map { it.toPostComment() }
                    LoadResult.Page(
                        data = posts,
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = if (posts.isEmpty()) null else currentPage + 1
                    )
                }

                else -> LoadResult.Error(Exception(Constants.UNEXPECTED_ERROR))
            }
        } catch (ioException: IOException) {
            LoadResult.Error(Exception(Constants.NO_INTERNET_ERROR_MESSAGE))
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}