package com.skymilk.socialapp.store.domain.usecase.follows

import com.skymilk.socialapp.store.domain.repository.FollowsRepository
import com.skymilk.socialapp.store.data.util.Result

class FollowOrUnFollow(
    private val followsRepository: FollowsRepository
) {

    suspend operator fun invoke(followedUserId: Long, shouldFollow: Boolean): Result<Boolean> {
        return followsRepository.followOfUnFollow(followedUserId, shouldFollow)
    }

}