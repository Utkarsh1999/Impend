package com.impend.shared.data.repository

import com.impend.shared.domain.repository.PreferencesRepository
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreferencesRepositoryImpl(private val settings: Settings) : PreferencesRepository {

    private val KEY_CURRENCY = "selected_currency_code"
    private val KEY_LANGUAGE = "selected_language_code"

    private val _currencyCode = MutableStateFlow(settings.getString(KEY_CURRENCY, "USD"))
    override val currencyCode: StateFlow<String> = _currencyCode.asStateFlow()

    private val _languageCode = MutableStateFlow(settings.getString(KEY_LANGUAGE, "en"))
    override val languageCode: StateFlow<String> = _languageCode.asStateFlow()

    override fun setCurrencyCode(code: String) {
        settings[KEY_CURRENCY] = code
        _currencyCode.value = code
    }

    override fun setLanguageCode(code: String) {
        settings[KEY_LANGUAGE] = code
        _languageCode.value = code
    }
}
