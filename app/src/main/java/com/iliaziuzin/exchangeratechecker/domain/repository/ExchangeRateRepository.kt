package com.iliaziuzin.exchangeratechecker.domain.repository

interface ExchangeRateRepository {
    fun getRatesForFavorites():List<String>
    fun getRatesForCurrentCurrencies():List<String>
}