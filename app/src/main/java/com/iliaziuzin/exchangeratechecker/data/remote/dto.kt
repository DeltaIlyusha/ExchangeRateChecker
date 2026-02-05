package com.iliaziuzin.exchangeratechecker.data.remote

import kotlinx.serialization.Serializable

typealias CurrencyCode = String

@Serializable
data class SymbolsResponse(
    val success: Boolean,
    val symbols: Map<CurrencyCode, String>
)

@Serializable
data class LatestRatesResponse(
    val success: Boolean,
    val timestamp: Long,
    val base: CurrencyCode,
    val date: String,
    val rates: Map<CurrencyCode, Double>
)

