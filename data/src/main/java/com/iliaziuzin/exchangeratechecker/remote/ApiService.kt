package com.iliaziuzin.exchangeratechecker.remote

import com.iliaziuzin.exchangeratechecker.models.CurrencyCodeDto
import com.iliaziuzin.exchangeratechecker.models.LatestRatesResponse
import com.iliaziuzin.exchangeratechecker.models.SymbolsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("symbols")
    suspend fun getSymbols(): SymbolsResponse

    @GET("latest")
    suspend fun getLatestRates(
        @Query("base") base: CurrencyCodeDto? = null,
        @Query("symbols") symbols: String? = null
    ): LatestRatesResponse
}
