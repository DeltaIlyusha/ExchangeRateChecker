package com.iliaziuzin.exchangeratechecker.domain.models

data class CurrencyExchangePair (
    val from:String,
    val to:String,
    val rate:Double
)