package com.skymilk.socialapp.store.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skymilk.socialapp.store.data.local.UserPreferences
import com.skymilk.socialapp.store.data.remote.PostApiService
import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.util.Constants
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException
import kotlin.let

internal class UserPostsPagingSource(
    private val postApiService: PostApiService,
    private val userPreferences: UserPreferences,
    private val userId: Long
) : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        //키 값이 없다면 다시 첫페이지부터 (0이 시작일 수도 있다)
        val currentPage = params.key ?: Constants.FIRST_PAGE_INDEX

        return try {
            val userData = userPreferences.getUserData()
            val response = postApiService.getUserPosts(
                token = userData.token,
                userId = userId,
                currentUserId = userData.id,
                page = currentPage,
                pageSize = params.loadSize
            )

            when (response.code) {
                HttpStatusCode.OK -> {
                    val posts = response.data.posts.map { it.toPost() }
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