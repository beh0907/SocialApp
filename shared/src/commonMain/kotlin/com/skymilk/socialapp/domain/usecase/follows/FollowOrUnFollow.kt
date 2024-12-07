package com.skymilk.socialapp.domain.usecase.follows

import com.skymilk.socialapp.domain.repository.FollowsRepository
import com.skymilk.socialapp.util.Result

class FollowOrUnFollow(
    private val followsRepository: FollowsRepository
) {

    suspend operator fun invoke(followedUserId: Long, shouldFollow: Boolean): Result<Boolean> {
        return followsRepository.followOfUnFollow(followedUserId, shouldFollow)
    }

}