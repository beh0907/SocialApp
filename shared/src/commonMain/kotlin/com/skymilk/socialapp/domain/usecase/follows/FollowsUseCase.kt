package com.skymilk.socialapp.domain.usecase.follows

data class FollowsUseCase(
    val getMyFollows: GetMyFollows,
    val getFollowableUsers: GetFollowableUsers,
    val followOrUnFollowUseCase: FollowOrUnFollow,
)
