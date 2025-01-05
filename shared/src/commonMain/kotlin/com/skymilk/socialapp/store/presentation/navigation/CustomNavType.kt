package com.skymilk.socialapp.store.presentation.navigation

import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import com.eygraber.uri.UriCodec
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.typeOf

inline fun <reified T> createGenericNavType(
    isNullableAllowed: Boolean = false
) = object : NavType<T>(isNullableAllowed) {
    override fun get(bundle: Bundle, key: String): T? {
        return bundle.getString(key)?.let { Json.decodeFromString<T>(it) }
    }

    override fun parseValue(value: String): T {
        return Json.decodeFromString<T>(UriCodec.decode(value))
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, Json.encodeToString(Json.serializersModule.serializer(), value))
    }

    override fun serializeAsValue(value: T): String {
        return UriCodec.encode(Json.encodeToString(Json.serializersModule.serializer(), value))
    }
}

// Extension function to create type map easily
inline fun <reified T> createTypeMap() = mapOf(typeOf<T>() to createGenericNavType<T>())