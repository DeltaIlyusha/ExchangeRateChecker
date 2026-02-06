package com.iliaziuzin.exchangeratechecker.domain.repository

import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyCode
import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.domain.models.ExchangeRate
import kotlinx.coroutines.flow.Flow


interface CurrencyRepository {

    suspend fun getSymbols(): Flow<Map<CurrencyCode, String>>

    suspend fun getLatestRates(
        base: String? = null,
        symbols: String? = null
    ): Flow<Map<CurrencyCode, CurrencyExchangePair>>
}
