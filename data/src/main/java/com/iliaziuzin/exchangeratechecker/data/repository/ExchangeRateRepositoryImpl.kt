package com.iliaziuzin.exchangeratechecker.data.repository

import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.domain.repository.CurrencyRepository
import com.iliaziuzin.exchangeratechecker.domain.repository.ExchangeRateRepository
import com.iliaziuzin.exchangeratechecker.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExchangeRateRepositoryImpl  @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val currencyRepository: CurrencyRepository,
) : ExchangeRateRepository {
    override fun getRatesForFavorites(): Flow<List<CurrencyExchangePair>> =  flow {
        listOf<CurrencyExchangePair>()
    }

    override fun getRatesForCurrentCurrencies(): Flow<List<CurrencyExchangePair>> = flow {
        listOf<CurrencyExchangePair>()
    }
}