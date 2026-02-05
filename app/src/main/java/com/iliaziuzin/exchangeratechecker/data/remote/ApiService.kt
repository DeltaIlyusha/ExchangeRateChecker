package com.iliaziuzin.exchangeratechecker.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("symbols")
    suspend fun getSymbols(): SymbolsResponse

    @GET("latest")
    suspend fun getLatestRates(
        @Query("base") base: CurrencyCode? = null,
        @Query("symbols") symbols: String? = null
    ): LatestRatesResponse
}
