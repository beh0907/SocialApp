package com.skymilk.socialapp.domain.usecase.follows

import app.cash.paging.PagingData
import com.skymilk.socialapp.domain.model.FollowsUser
import com.skymilk.socialapp.domain.repository.FollowsRepository
import kotlinx.coroutines.flow.Flow

class GetMyFollows(
    private val followsRepository: FollowsRepository
) {
    suspend operator fun invoke(userId: Long, followsType: Int): Flow<PagingData<FollowsUser>> {
        return followsRepository.getMyFollows(userId, followsType)
    }
}