package com.iliaziuzin.exchangeratechecker.data.repository

import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyCode
import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePairWithFavorite
import com.iliaziuzin.exchangeratechecker.domain.repository.CurrencyRepository
import com.iliaziuzin.exchangeratechecker.domain.repository.ExchangeRateRepository
import com.iliaziuzin.exchangeratechecker.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val currencyRepository: CurrencyRepository,
) : ExchangeRateRepository {
    override fun getRatesForFavorites(): Flow<List<CurrencyExchangePairWithFavorite>> {
        val latestRatesFlow = currencyRepository.getLatestRates()
        val favoritesFlow = favoriteRepository.getFavorites()

        return combine(latestRatesFlow, favoritesFlow) { rates, favorites ->
            favorites.map {
                CurrencyExchangePairWithFavorite(
                    from = it.from,
                    to = it.to,
                    rate = 0.0,
                    isFavorite = true
                )
            }
        }.catch {
            emit(emptyList())
        }.flowOn(Dispatchers.IO)
    }


    override fun getLatestRatesForCurrency(base: CurrencyCode): Flow<List<CurrencyExchangePairWithFavorite>> {
        val symbolsFlow = currencyRepository.getSymbols()
        val favoritesFlow = favoriteRepository.getFavorites()

        return symbolsFlow.flatMapLatest { symbolsMap ->
            val symbols = symbolsMap.keys.joinToString(",")
            val latestRatesFlow = currencyRepository.getLatestRates(base = base, symbols = symbols)

            combine(latestRatesFlow, favoritesFlow) { rates, favorites ->
                rates.values.map {
                    CurrencyExchangePairWithFavorite(
                        from = it.from,
                        to = it.to,
                        rate = it.rate,
                        isFavorite = favorites.any { favorite ->
                            favorite.from == it.from && favorite.to == it.to
                        }
                    )
                }
            }
        }.catch {
            emit(emptyList())
        }.flowOn(Dispatchers.IO)
    }
}