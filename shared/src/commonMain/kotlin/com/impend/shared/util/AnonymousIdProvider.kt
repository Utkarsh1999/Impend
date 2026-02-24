package com.impend.shared.util

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

/**
 * ARCH-003: AnonymousIdProvider
 * Generates and persists a stable but anonymous identifier to prevent hardware fingerprinting
 * while still allowing "Pro" status verification across sessions.
 */
class AnonymousIdProvider(private val settings: Settings) {
    
    fun getAnonymousId(): String {
        val existing: String? = settings[KEY_ANONYMOUS_ID]
        if (existing != null) return existing
        
        val newId = generateRandomString(24)
        settings[KEY_ANONYMOUS_ID] = newId
        return newId
    }

    private fun generateRandomString(length: Int): String {
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { i -> charPool.random() }
            .joinToString("")
    }

    companion object {
        private const val KEY_ANONYMOUS_ID = "anonymous_device_id"
    }
}
