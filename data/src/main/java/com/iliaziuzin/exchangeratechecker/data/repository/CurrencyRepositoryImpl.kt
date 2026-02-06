package com.iliaziuzin.exchangeratechecker.data.repository

import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyCode
import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.domain.repository.CurrencyRepository
import com.iliaziuzin.exchangeratechecker.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CurrencyRepository {

    private val ratesCache = mutableMapOf<String, Pair<Long, Map<String, Double>>>()
    private val CACHE_LIFETIME_MS = TimeUnit.MINUTES.toMillis(5)

    override fun getSymbols(): Flow<Map<CurrencyCode, String>> = flow {
        val response = apiService.getSymbols()
        if (response.success) {
            emit(response.symbols)
        } else {
            throw Exception("Failed to fetch symbols")
        }
    }

    override fun getLatestRates(base: String?, symbols: String?): Flow<Map<CurrencyCode, CurrencyExchangePair>> = flow {
        val baseCurrency = base ?: "EUR"
        val currentTime = System.currentTimeMillis()
        val cachedEntry = ratesCache[baseCurrency]

        val ratesFromNetwork = if (cachedEntry == null || (currentTime - cachedEntry.first) >= CACHE_LIFETIME_MS) {
            val response = apiService.getLatestRates(baseCurrency)
            if (response.success) {
                ratesCache[baseCurrency] = currentTime to response.rates
                response.rates
            } else {
                throw Exception("Failed to fetch latest rates for $baseCurrency")
            }
        } else {
            cachedEntry.second
        }

        emit(filterRates(baseCurrency, ratesFromNetwork, symbols))
    }

    private fun filterRates(base: String, rates: Map<String, Double>, symbols: String?): Map<CurrencyCode, CurrencyExchangePair> {
        val symbolList = symbols?.split(',')
        val filteredRates = if (symbolList != null) {
            rates.filterKeys { it in symbolList }
        } else {
            rates
        }
        return filteredRates.mapValues { (key, value) ->
            CurrencyExchangePair(
                from = base,
                to = key,
                rate = value,
            )
        }
    }
}
