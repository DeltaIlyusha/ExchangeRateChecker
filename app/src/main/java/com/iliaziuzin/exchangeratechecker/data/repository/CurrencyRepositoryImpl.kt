package com.iliaziuzin.exchangeratechecker.data.repository

import com.iliaziuzin.exchangeratechecker.data.remote.ApiService
import com.iliaziuzin.exchangeratechecker.data.remote.ConvertResponse
import com.iliaziuzin.exchangeratechecker.data.remote.CurrencyCode
import com.iliaziuzin.exchangeratechecker.data.remote.LatestRatesResponse
import com.iliaziuzin.exchangeratechecker.data.remote.SymbolsResponse
import com.iliaziuzin.exchangeratechecker.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CurrencyRepository {

    override suspend fun getSymbols(): SymbolsResponse = apiService.getSymbols()

    override suspend fun convert(
        amount: String,
        from: CurrencyCode,
        to: CurrencyCode,
        date: String?
    ): ConvertResponse = apiService.convert(amount, from, to, date)

    override suspend fun getLatestRates(
        base: CurrencyCode?,
        symbols: String?
    ): LatestRatesResponse = apiService.getLatestRates(base, symbols)
}
