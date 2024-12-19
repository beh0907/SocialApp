package com.skymilk.socialapp.store.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skymilk.socialapp.store.data.local.UserPreferences
import com.skymilk.socialapp.store.data.remote.FollowApiService
import com.skymilk.socialapp.store.domain.model.FollowsUser
import com.skymilk.socialapp.util.Constants
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException
import kotlin.let

internal class FollowsPagingSource(
    private val followApiService: FollowApiService,
    private val userPreferences: UserPreferences,
    private val userId: Long,
    private val followsType: Int
) : PagingSource<Int, FollowsUser>() {
    override fun getRefreshKey(state: PagingState<Int, FollowsUser>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FollowsUser> {
        //키 값이 없다면 다시 첫페이지부터 (0이 시작일 수도 있다)
        val currentPage = params.key ?: Constants.FIRST_PAGE_INDEX

        return try {
            val userData = userPreferences.getUserData()
            val response = followApiService.getMyFollows(
                token = userData.token,
                userId = userId,
                page = currentPage,
                pageSize = params.loadSize,
                followsEndPoint = if (followsType == 1) "followers" else "following"
            )

            when (response.code) {
                HttpStatusCode.OK -> {
                    val posts = response.data.follows.map { it.toDomainFollowUser() }
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