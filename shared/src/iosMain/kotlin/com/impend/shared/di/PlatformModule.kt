package com.impend.shared.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.impend.shared.data.local.ImpendDatabase
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.dsl.module

val platformModule = module {
    single {
        val driver = NativeSqliteDriver(ImpendDatabase.Schema, "impend.db")
        ImpendDatabase(driver)
    }
    single<Settings> {
        NSUserDefaultsSettings(platform.Foundation.NSUserDefaults.standardUserDefaults)
    }
}
