package com.skymilk.socialapp.android.presentation.common.dummy

data class Profile(
    val id: Long,
    val name: String,
    val bio: String,
    val profileUrl: String,
    val followersCount: Int,
    val followingCount: Int,
    val isOwnProfile: Boolean = false,
    val isFollowing: Boolean = false
)

val sampleProfiles = listOf(
    Profile(
        id = 1,
        name = "박사장",
        bio = "반갑다",
        profileUrl = "https://picsum.photos/200/200?random=1",
        followersCount = 125,
        followingCount = 59,
        isOwnProfile = true,
        isFollowing = true
    ),
    Profile(
        id = 2,
        name = "김펭귄",
        bio = "반갑다",
        profileUrl = "https://picsum.photos/200/200?random=1",
        followersCount = 76,
        followingCount = 23,
        isOwnProfile = true,
        isFollowing = true
    ),
    Profile(
        id = 3,
        name = "정상호",
        bio = "반갑다",
        profileUrl = "https://picsum.photos/200/200?random=1",
        followersCount = 34,
        followingCount = 471,
        isOwnProfile = true,
        isFollowing = true
    ),
    Profile(
        id = 4,
        name = "김창수",
        bio = "반갑다",
        profileUrl = "https://picsum.photos/200/200?random=1",
        followersCount = 104,
        followingCount = 82,
        isOwnProfile = true,
        isFollowing = true
    ),
    Profile(
        id = 5,
        name = "최선욱",
        bio = "반갑다",
        profileUrl = "https://picsum.photos/200/200?random=1",
        followersCount = 5,
        followingCount = 12,
        isOwnProfile = true,
        isFollowing = true
    ),
)