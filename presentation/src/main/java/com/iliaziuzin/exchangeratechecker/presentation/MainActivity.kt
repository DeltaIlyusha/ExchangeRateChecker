package com.iliaziuzin.exchangeratechecker.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.iliaziuzin.exchangeratechecker.presentation.main.MainScreen
import com.iliaziuzin.exchangeratechecker.ui.theme.ExchangeRateCheckerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(scrim = Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(scrim = Color.TRANSPARENT, Color.TRANSPARENT)
        )
        setContent {
            ExchangeRateCheckerTheme {
                MainScreen()
            }
        }
    }
}
