package com.skymilk.socialapp.store.domain.usecase.follows

import com.skymilk.socialapp.store.domain.model.FollowsUser
import com.skymilk.socialapp.store.domain.repository.FollowsRepository
import com.skymilk.socialapp.store.data.util.Result

class GetFollowableUsers(
    private val followsRepository: FollowsRepository
) {
    suspend operator fun invoke(): Result<List<FollowsUser>> {
        return followsRepository.getFollowableUsers()
    }
}