package com.impend.shared.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.impend.shared.data.local.ImpendDatabase
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.dsl.module

val platformModule = module {
    single {
        val driver = AndroidSqliteDriver(ImpendDatabase.Schema, get(), "impend.db")
        ImpendDatabase(driver)
    }
    single<Settings> {
        SharedPreferencesSettings(
            get<android.content.Context>().getSharedPreferences("impend_prefs", android.content.Context.MODE_PRIVATE)
        )
    }
}
