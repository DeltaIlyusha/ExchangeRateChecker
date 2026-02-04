package com.iliaziuzin.exchangeratechecker.data.remote

import kotlinx.serialization.Serializable

typealias CurrencyCode = String

@Serializable
data class SymbolsResponse(
    val success: Boolean,
    val symbols: Map<CurrencyCode, String>
)

@Serializable
data class ConvertResponse(
    val success: Boolean,
    val query: Query,
    val info: Info,
    val date: String,
    val result: Double
)

@Serializable
data class LatestRatesResponse(
    val success: Boolean,
    val timestamp: Long,
    val base: CurrencyCode,
    val date: String,
    val rates: Map<CurrencyCode, Double>
)

@Serializable
data class Query(
    val from: CurrencyCode,
    val to: CurrencyCode,
    val amount: Int
)

@Serializable
data class Info(
    val timestamp: Long,
    val rate: Double
)
