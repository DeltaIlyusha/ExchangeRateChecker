package com.iliaziuzin.exchangeratechecker.data.repository

import com.iliaziuzin.exchangeratechecker.domain.repository.CurrencyRepository
import com.iliaziuzin.exchangeratechecker.domain.repository.ExchangeRateRepository
import com.iliaziuzin.exchangeratechecker.domain.repository.FavoriteRepository
import javax.inject.Inject

class ExchangeRateRepositoryImpl  @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val currencyRepository: CurrencyRepository,
) : ExchangeRateRepository {
    override fun getRatesForFavorites(): List<String> {
        TODO("Not yet implemented")
    }

    override fun getRatesForCurrentCurrencies(): List<String> {
        TODO("Not yet implemented")
    }
}