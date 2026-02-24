package com.impend.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.impend.app.di.appModule
import com.impend.shared.di.initKoin
import com.impend.shared.di.platformModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            App()
        }
    }
}
