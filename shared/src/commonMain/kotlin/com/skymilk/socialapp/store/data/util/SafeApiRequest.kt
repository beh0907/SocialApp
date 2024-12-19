package com.skymilk.socialapp.store.data.util

import com.skymilk.socialapp.util.Constants
import com.skymilk.socialapp.util.DispatcherProvider
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import kotlin.coroutines.cancellation.CancellationException

internal suspend fun <T> safeApiRequest(
    dispatcher: DispatcherProvider,
    errorHandler: (Throwable) -> Result<T> = { defaultErrorHandler(it) },
    apiCall: suspend () -> Result<T>
): Result<T> = withContext(dispatcher.io) {
    try {
        apiCall()
    } catch (exception: Throwable) {
        if (exception is CancellationException) throw exception
        errorHandler(exception)
    }
}

private fun <T> defaultErrorHandler(error: Throwable): Result<T> {
    return if (error is IOException) {
        Result.Error(message = Constants.NO_INTERNET_ERROR_MESSAGE)
    } else {
        Result.Error(message = error.message ?: Constants.UNEXPECTED_ERROR)
    }
}