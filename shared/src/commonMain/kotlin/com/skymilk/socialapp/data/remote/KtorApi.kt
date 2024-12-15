package com.skymilk.socialapp.data.remote

import com.skymilk.socialapp.util.Constants
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal abstract class KtorApi {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // 일치하지 않는 키 무시
                useAlternativeNames = false // 주석 사용X
            })
        }

        //타임 아웃 설정
        install(HttpTimeout) {
            // 연결 타임아웃 (밀리초 단위)
            connectTimeoutMillis = 5000 // 5초

            // 요청 타임아웃 (밀리초 단위)
            requestTimeoutMillis = 10000 // 10초

            // 소켓 타임아웃 (밀리초 단위)
            socketTimeoutMillis = 10000 // 10초
        }
    }

    //엔드 포인트 설정
    fun HttpRequestBuilder.endPoint(path: String) {
        url {
            takeFrom(Constants.BASE_URL)
            path(path)
            contentType(ContentType.Application.Json)
        }

    }

    //JWT 토큰 헤더 추가
    fun HttpRequestBuilder.setToken(token: String ) {
        headers {
            append("Authorization", "Bearer $token")
        }
    }

    //MultiPart 설정
    fun HttpRequestBuilder.setupMultipartRequest() {
        contentType(ContentType.MultiPart.FormData)
    }
}