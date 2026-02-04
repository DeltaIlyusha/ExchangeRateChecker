package com.iliaziuzin.exchangeratechecker.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("symbols")
    suspend fun getSymbols(): SymbolsResponse

    @GET("convert")
    suspend fun convert(
        @Query("amount") amount: String,
        @Query("from") from: CurrencyCode,
        @Query("to") to: CurrencyCode,
        @Query("date") date: String? = null
    ): ConvertResponse

    @GET("latest")
    suspend fun getLatestRates(
        @Query("base") base: CurrencyCode? = null,
        @Query("symbols") symbols: String? = null
    ): LatestRatesResponse
}
