package com.impend.shared.di

import com.impend.shared.data.remote.AuthApi
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/**
 * ARCH-003: PrivacyGuard
 * Interceptor that enforces the "Zero-Leak" protocol by whitelisting allowed domains
 * and blocking any request that doesn't meet security criteria.
 */
private val PrivacyGuardPlugin = createClientPlugin("PrivacyGuard") {
    onRequest { request, _ ->
        val url = request.url.buildString().lowercase()
        
        // Whitelist enforcement: Only Impend API is allowed
        if (!url.contains("api.impend.finance")) {
            throw IllegalStateException("PRIVACY VIOLATION: Unauthorized network access to $url. All financial data must remain local.")
        }
        
        // Security Check: Ensure sensitive headers are not present if not required
        // (Add more granular checks here as needed)
    }
}

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            
            // ARCH-003 Enforcement
            install(PrivacyGuardPlugin)
        }
    }
    
    single { AuthApi(get()) }
}
