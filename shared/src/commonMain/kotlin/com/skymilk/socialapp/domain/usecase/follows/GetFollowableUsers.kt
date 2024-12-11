package com.skymilk.socialapp.domain.usecase.follows

import com.skymilk.socialapp.domain.model.FollowsUser
import com.skymilk.socialapp.domain.repository.FollowsRepository
import com.skymilk.socialapp.data.util.Result

class GetFollowableUsers(
    private val followsRepository: FollowsRepository
) {
    suspend operator fun invoke(): Result<List<FollowsUser>> {
        return followsRepository.getFollowableUsers()
    }
}