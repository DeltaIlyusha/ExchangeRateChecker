package com.iliaziuzin.exchangeratechecker.models

data class UiCurrencyExchangePair (
    val from:String,
    val to:String,
    val rate:Double,
    val isFavorite: Boolean
)