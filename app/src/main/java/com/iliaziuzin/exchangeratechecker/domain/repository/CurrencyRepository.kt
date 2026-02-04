package com.iliaziuzin.exchangeratechecker.domain.repository

import com.iliaziuzin.exchangeratechecker.data.remote.ConvertResponse
import com.iliaziuzin.exchangeratechecker.data.remote.CurrencyCode
import com.iliaziuzin.exchangeratechecker.data.remote.LatestRatesResponse
import com.iliaziuzin.exchangeratechecker.data.remote.SymbolsResponse


interface CurrencyRepository {

    suspend fun getSymbols(): SymbolsResponse

    suspend fun convert(
        amount: String,
        from: CurrencyCode,
        to: CurrencyCode,
        date: String? = null
    ): ConvertResponse

    suspend fun getLatestRates(
        base: CurrencyCode? = null,
        symbols: String? = null
    ): LatestRatesResponse
}
