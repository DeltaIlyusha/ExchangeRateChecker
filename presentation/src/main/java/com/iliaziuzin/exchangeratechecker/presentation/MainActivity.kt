package com.iliaziuzin.exchangeratechecker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.iliaziuzin.exchangeratechecker.presentation.main.MainScreen
import com.iliaziuzin.exchangeratechecker.presentation.ui.theme.ExchangeRateCheckerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToG()
        setContent {
            ExchangeRateCheckerTheme {
                MainScreen()
            }
        }
    }
}
