package com.impend.app

import androidx.compose.ui.window.ComposeUIViewController
import com.impend.app.di.appModule
import com.impend.shared.di.initKoin
import com.impend.shared.di.platformModule

fun MainViewController() = ComposeUIViewController { 
    initKoin(
        appModule = appModule,
        platformModule = platformModule
    )
    App() 
}
