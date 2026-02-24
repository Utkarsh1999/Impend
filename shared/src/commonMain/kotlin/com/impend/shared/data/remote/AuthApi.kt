package com.impend.shared.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

class AuthApi(private val client: HttpClient) {

    suspend fun login(request: AuthRequest): AuthResponse {
        return client.post("https://api.impend.finance/v1/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun getSubscriptionStatus(): SubscriptionStatus {
        return client.get("https://api.impend.finance/v1/subscriptions/status").body()
    }
}

@Serializable
data class AuthRequest(val email: String, val password: String)

@Serializable
data class AuthResponse(val accessToken: String, val refreshToken: String, val user: UserDto)

@Serializable
data class UserDto(val id: String, val email: String)

@Serializable
data class SubscriptionStatus(val isPro: Boolean, val expiresAt: String, val entitlements: List<String>)
