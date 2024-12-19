package com.skymilk.socialapp.store.domain.repository

import app.cash.paging.PagingData
import com.skymilk.socialapp.store.domain.model.FollowsUser
import com.skymilk.socialapp.store.data.util.Result
import kotlinx.coroutines.flow.Flow

interface FollowsRepository {
    suspend fun getFollowableUsers(): Result<List<FollowsUser>>

    suspend fun getMyFollows(userId: Long, followsType: Int): Flow<PagingData<FollowsUser>>

    suspend fun followOfUnFollow(followedUserId: Long, shouldFollow: Boolean): Result<Boolean>
}