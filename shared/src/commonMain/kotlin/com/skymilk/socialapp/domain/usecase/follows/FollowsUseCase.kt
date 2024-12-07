package com.skymilk.socialapp.domain.usecase.follows

data class FollowsUseCase(
    val followOrUnFollowUseCase: FollowOrUnFollow,
    val getFollowableUsers: GetFollowableUsers,
)
