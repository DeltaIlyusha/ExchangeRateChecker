package com.iliaziuzin.exchangeratechecker.data.repository

import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyCode
import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.domain.repository.CurrencyRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

class FakeCurrencyRepository @Inject constructor() : CurrencyRepository {

    private val symbols = mapOf(
        "EUR" to "Euro",
        "JPY" to "Japanese Yen",
        "RUB" to "Russian Ruble",
        "USD" to "United States Dollar",
    )

    override fun getSymbols(): Flow<Map<CurrencyCode, String>> = flow {
        delay(1000)
        emit(symbols)
    }

    override fun getLatestRates(base: CurrencyCode?, symbols: String?): Flow<Map<CurrencyCode, CurrencyExchangePair>> = flow {
        delay(1000)
        val baseCurrency = base ?: "USD"
        val targetSymbols = symbols?.split(',') ?: listOf("EUR", "RUB", "GBP")

        val rates = targetSymbols.associateWith {
            CurrencyExchangePair(
                from = baseCurrency,
                to = it,
                rate = Random.nextDouble(0.5, 1.5)
            )
        }
        emit(rates)
    }
}
