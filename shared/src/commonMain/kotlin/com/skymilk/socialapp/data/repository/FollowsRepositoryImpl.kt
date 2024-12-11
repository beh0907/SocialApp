package com.skymilk.socialapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.cash.paging.PagingData
import com.skymilk.socialapp.data.local.UserPreferences
import com.skymilk.socialapp.data.model.FollowsParams
import com.skymilk.socialapp.data.paging.FollowsPagingSource
import com.skymilk.socialapp.data.remote.FollowApiService
import com.skymilk.socialapp.util.Constants
import com.skymilk.socialapp.util.DispatcherProvider
import com.skymilk.socialapp.data.util.Result
import com.skymilk.socialapp.data.util.safeApiRequest
import com.skymilk.socialapp.domain.model.FollowsUser
import com.skymilk.socialapp.domain.repository.FollowsRepository
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

internal class FollowsRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val followApiService: FollowApiService,
    private val userPreferences: UserPreferences
) : FollowsRepository {
    override suspend fun getFollowableUsers(): Result<List<FollowsUser>> {
        return safeApiRequest(dispatcher) {
            //DataStore로부터 유저 정보를 가져와 유저목록 요청
            val userData = userPreferences.getUserData()
            val response = followApiService.getFollowableUsers(userData.token, userData.id)

            //코드 상태에 따라 리턴
            when (response.code) {
                HttpStatusCode.OK -> Result.Success(data = response.data.follows.map { it.toDomainFollowUser() })

                HttpStatusCode.BadRequest -> Result.Error(message = "${response.data.message}")

                HttpStatusCode.Forbidden -> Result.Success(data = emptyList())

                else -> Result.Error(message = "${response.data.message}")
            }
        }
    }

    override suspend fun getMyFollows(
        userId: Long,
        followsType: Int
    ): Flow<PagingData<FollowsUser>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.PAGE_SIZE),
            pagingSourceFactory = {
                FollowsPagingSource(
                    followApiService = followApiService,
                    userPreferences = userPreferences,
                    userId = userId,
                    followsType = followsType
                )
            }
        )
            .flow
            .flowOn(dispatcher.io)
    }

    override suspend fun followOfUnFollow(
        followedUserId: Long,
        shouldFollow: Boolean
    ): Result<Boolean> {
        return safeApiRequest(dispatcher) {
            //팔로우 파라미터 생성 후 shouldFollow에 따라 처리
            val userData = userPreferences.getUserData()
            val params = FollowsParams(userData.id, followedUserId)
            val response = if (shouldFollow) followApiService.followUser(userData.token, params)
            else followApiService.unFollowUser(userData.token, params)

            //코드 상태에 따라 리턴
            when (response.code) {
                HttpStatusCode.OK -> Result.Success(data = true)

                else -> Result.Error(message = "${response.data.message}")
            }
        }
    }
}