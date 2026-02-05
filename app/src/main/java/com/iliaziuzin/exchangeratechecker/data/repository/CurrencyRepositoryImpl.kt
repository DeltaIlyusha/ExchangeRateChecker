package com.iliaziuzin.exchangeratechecker.data.repository

import com.iliaziuzin.exchangeratechecker.data.remote.ApiService
import com.iliaziuzin.exchangeratechecker.data.remote.CurrencyCode
import com.iliaziuzin.exchangeratechecker.data.remote.LatestRatesResponse
import com.iliaziuzin.exchangeratechecker.data.remote.SymbolsResponse
import com.iliaziuzin.exchangeratechecker.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CurrencyRepository {

    override suspend fun getSymbols(): SymbolsResponse {
        return apiService.getSymbols()
    }

    override suspend fun getLatestRates(base: CurrencyCode?, symbols: String?): LatestRatesResponse {
        return apiService.getLatestRates(base, symbols)
    }
}
