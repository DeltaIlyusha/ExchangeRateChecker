package com.iliaziuzin.exchangeratechecker.domain.models

data class CurrencyExchangePairWithFavorite (
    val from: CurrencyCode,
    val to:CurrencyCode,
    val rate:ExchangeRate,
    val isFavorite: Boolean
)