package com.skymilk.socialapp.data.util

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val message: String? = null) : Result<T>()
}