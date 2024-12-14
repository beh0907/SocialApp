package com.skymilk.socialapp.android.presentation.util

import android.content.Context
import android.net.Uri
import com.skymilk.socialapp.data.util.Result
import com.skymilk.socialapp.util.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageBytesReader(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun readImageBytes(uri: Uri): Result<ByteArray> {
        return withContext(ioDispatcher) {
            val bytes = context.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.readBytes()
            }

            bytes?.let { Result.Success(it) } ?: Result.Error(message = Constants.READING_IMAGE_BYTES_ERROR_MESSAGE)
        }
    }
}