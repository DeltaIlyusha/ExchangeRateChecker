package com.iliaziuzin.exchangeratechecker.domain.repository

import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePairWithFavorite
import kotlinx.coroutines.flow.Flow

interface ExchangeRateRepository {
    fun getRatesForFavorites(): Flow<List<CurrencyExchangePairWithFavorite>>
    fun getLatestRatesForCurrency(base: String):Flow<List<CurrencyExchangePairWithFavorite>>
}
