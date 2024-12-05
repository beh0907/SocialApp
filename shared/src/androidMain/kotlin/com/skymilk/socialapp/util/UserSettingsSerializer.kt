package com.skymilk.socialapp.util

import androidx.datastore.core.Serializer
import com.skymilk.socialapp.data.model.UserSettings
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.io.readBytes
import kotlin.text.decodeToString
import kotlin.text.encodeToByteArray

object UserSettingsSerializer : Serializer<UserSettings> {
    override val defaultValue: UserSettings
        get() = UserSettings()

    override suspend fun readFrom(input: java.io.InputStream): UserSettings {
        return try {
            Json.decodeFromString(
                deserializer = UserSettings.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            defaultValue
        }
    }

    override suspend fun writeTo(
        t: UserSettings,
        output: java.io.OutputStream
    ) {
        output.write(
            Json.encodeToString(
                serializer = UserSettings.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }


}