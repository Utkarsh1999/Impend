package com.impend.shared.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface PreferencesRepository {
    val currencyCode: StateFlow<String>
    val languageCode: StateFlow<String>

    fun setCurrencyCode(code: String)
    fun setLanguageCode(code: String)
}
