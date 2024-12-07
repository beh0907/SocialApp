package com.skymilk.socialapp.domain.repository

import com.skymilk.socialapp.domain.model.FollowsUser
import com.skymilk.socialapp.util.Result

interface FollowsRepository {
    suspend fun getFollowableUsers(): Result<List<FollowsUser>>

    suspend fun followOfUnFollow(followedUserId: Long, shouldFollow: Boolean): Result<Boolean>
}