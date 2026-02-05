package com.iliaziuzin.exchangeratechecker.data.models

import kotlinx.serialization.Serializable

typealias CurrencyCodeDto = String

@Serializable
data class SymbolsResponse(
    val success: Boolean,
    val symbols: Map<CurrencyCodeDto, String>
)

@Serializable
data class LatestRatesResponse(
    val success: Boolean,
    val timestamp: Long,
    val base: CurrencyCodeDto,
    val date: String,
    val rates: Map<CurrencyCodeDto, Double>
)

