package com.iliaziuzin.exchangeratechecker.data.repository

import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyCode
import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePairWithFavorite
import com.iliaziuzin.exchangeratechecker.domain.repository.CurrencyRepository
import com.iliaziuzin.exchangeratechecker.domain.repository.ExchangeRateRepository
import com.iliaziuzin.exchangeratechecker.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExchangeRateRepositoryImpl  @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val currencyRepository: CurrencyRepository,
) : ExchangeRateRepository {
    override fun getRatesForFavorites(): Flow<List<CurrencyExchangePairWithFavorite>> =  flow {
        listOf<CurrencyExchangePair>()
    }



    override fun getLatestRatesForCurrency(base: CurrencyCode): Flow<List<CurrencyExchangePairWithFavorite>> = flow {
        val currencies = mutableListOf<CurrencyCode>()


        currencyRepository.getSymbols().collect { symbols ->
            symbols.forEach { (code, name) ->
                currencies.add(code)
            }
        }

        val rates = currencyRepository.getLatestRates(
            base = base,
            symbols = currencies.joinToString(",")
        )

        val favoriteCurrencies = favoriteRepository.getFavorites()

        /*combine(currencies, rates, favoriteCurrencies) { currencies, rates, favorites ->

        }*/


        emit( listOf<CurrencyExchangePairWithFavorite>())
    }
}