package com.impend.app

import android.app.Application
import com.impend.app.di.appModule
import com.impend.shared.di.initKoin
import com.impend.shared.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class ImpendApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin(
            appModule = appModule,
            platformModule = platformModule,
            appDeclaration = {
                androidLogger()
                androidContext(this@ImpendApp)
            }
        )
    }
}
