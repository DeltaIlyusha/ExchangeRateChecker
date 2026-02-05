package com.iliaziuzin.exchangeratechecker.domain.repository

import com.iliaziuzin.exchangeratechecker.data.remote.CurrencyCode
import com.iliaziuzin.exchangeratechecker.data.remote.LatestRatesResponse
import com.iliaziuzin.exchangeratechecker.data.remote.SymbolsResponse


interface CurrencyRepository {

    suspend fun getSymbols(): SymbolsResponse

    suspend fun getLatestRates(
        base: CurrencyCode? = null,
        symbols: String? = null
    ): LatestRatesResponse
}
