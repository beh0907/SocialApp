package com.skymilk.socialapp.store.domain.usecase.follows

import app.cash.paging.PagingData
import com.skymilk.socialapp.store.domain.model.FollowsUser
import com.skymilk.socialapp.store.domain.repository.FollowsRepository
import kotlinx.coroutines.flow.Flow

class GetMyFollows(
    private val followsRepository: FollowsRepository
) {
    suspend operator fun invoke(userId: Long, followsType: Int): Flow<PagingData<FollowsUser>> {
        return followsRepository.getMyFollows(userId, followsType)
    }
}