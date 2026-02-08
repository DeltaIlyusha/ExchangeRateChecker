package com.iliaziuzin.exchangeratechecker.repository

import android.util.Log
import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyCode
import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePairWithFavorite
import com.iliaziuzin.exchangeratechecker.domain.repository.CurrencyRepository
import com.iliaziuzin.exchangeratechecker.domain.repository.ExchangeRateRepository
import com.iliaziuzin.exchangeratechecker.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRateRepositoryImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val currencyRepository: CurrencyRepository,
) : ExchangeRateRepository {
    override fun getRatesForFavorites(): Flow<List<CurrencyExchangePairWithFavorite>> {
        return favoriteRepository.getFavorites().flatMapLatest { favorites ->
            if (favorites.isEmpty()) {
                return@flatMapLatest flowOf(emptyList<CurrencyExchangePairWithFavorite>())
            }

            val favoritesByFrom = favorites.groupBy { it.from }

            val favoriteRateFlows = favoritesByFrom.map { (from, favoriteList) ->
                val toSymbols = favoriteList.map { it.to }.joinToString(",")
                Log.d(ExchangeRateRepository::class.simpleName, "Requesting rates for $from with symbols $toSymbols")
                currencyRepository.getLatestRates(base = from, symbols = toSymbols)
            }

            combine(favoriteRateFlows) { rateMapsArray ->
                rateMapsArray.flatMap { it.values }
                    .map { currencyExchangePair ->
                        CurrencyExchangePairWithFavorite(
                            from = currencyExchangePair.from,
                            to = currencyExchangePair.to,
                            rate = currencyExchangePair.rate,
                            isFavorite = true
                        )
                    }
            }
        }.flowOn(Dispatchers.IO)
    }


    override fun getLatestRatesForCurrency(base: CurrencyCode): Flow<List<CurrencyExchangePairWithFavorite>> {
        val symbolsFlow = currencyRepository.getSymbols()
        val favoritesFlow = favoriteRepository.getFavorites()

        val ratesFlow: Flow<Map<CurrencyCode, CurrencyExchangePair>> = symbolsFlow.flatMapLatest { symbolsMap ->
            val symbols = symbolsMap.keys.joinToString(",")
            currencyRepository.getLatestRates(base = base, symbols = symbols)
        }

        return combine(ratesFlow, favoritesFlow) { rates, favorites ->
            rates.values.map { currencyExchangePair ->
                CurrencyExchangePairWithFavorite(
                    from = currencyExchangePair.from,
                    to = currencyExchangePair.to,
                    rate = currencyExchangePair.rate,
                    isFavorite = favorites.any { favorite ->
                        favorite.from == currencyExchangePair.from && favorite.to == currencyExchangePair.to
                    }
                )
            }
        }.flowOn(Dispatchers.IO)
    }
}
