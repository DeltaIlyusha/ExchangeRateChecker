package com.iliaziuzin.exchangeratechecker.domain.repository

import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyCode
import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.domain.models.ExchangeRate
import kotlinx.coroutines.flow.Flow


interface CurrencyRepository {

    fun getSymbols(): Flow<Map<CurrencyCode, String>>

    fun getLatestRates(
        base: String? = null,
        symbols: String? = null
    ): Flow<Map<CurrencyCode, CurrencyExchangePair>>
}
