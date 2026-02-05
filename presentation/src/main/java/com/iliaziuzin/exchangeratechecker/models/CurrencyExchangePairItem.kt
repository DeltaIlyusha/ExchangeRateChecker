package com.iliaziuzin.exchangeratechecker.models

data class CurrencyExchangePairItem (
    val from:String,
    val to:String,
    val rate:Double
)