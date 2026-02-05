package com.iliaziuzin.exchangeratechecker.domain.repository

import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePair
import kotlinx.coroutines.flow.Flow

interface ExchangeRateRepository {
    fun getRatesForFavorites(): Flow<List<CurrencyExchangePair>>
    fun getRatesForCurrentCurrencies():Flow<List<CurrencyExchangePair>>
}
