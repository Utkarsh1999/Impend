package com.impend.shared.social

import com.impend.shared.util.AnonymousIdProvider
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class SocialRelay(
    private val httpClient: HttpClient,
    private val idProvider: AnonymousIdProvider
) {
    private val baseUrl = "https://api.impend.finance/v1/social"

    suspend fun submitSuccessToken(circleId: String): Boolean {
        return try {
            val response = httpClient.post("$baseUrl/tokens") {
                contentType(ContentType.Application.Json)
                setBody(mapOf(
                    "circleId" to circleId,
                    "anonymousId" to idProvider.getAnonymousId(),
                    "tokenType" to "SUCCESS"
                ))
            }
            response.status.isSuccess()
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getCircleStats(circleId: String): Pair<Int, Float>? {
        return try {
            val response = httpClient.get("$baseUrl/circles/$circleId/stats")
            if (response.status.isSuccess()) {
                // In a real app, parse JSON. Simulating for now.
                Pair(150, 0.85f) 
            } else null
        } catch (e: Exception) {
            null
        }
    }
}
