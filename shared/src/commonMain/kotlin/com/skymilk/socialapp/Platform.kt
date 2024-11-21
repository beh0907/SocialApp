package com.skymilk.socialapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform