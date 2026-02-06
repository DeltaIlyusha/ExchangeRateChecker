package com.iliaziuzin.exchangeratechecker.repository

import com.iliaziuzin.exchangeratechecker.remote.ApiService
import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyCode
import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CurrencyRepository {

    override fun getSymbols(): Flow<Map<CurrencyCode, String>> = flow {
        val response = apiService.getSymbols()
        if (response.success) {
            emit(response.symbols)
        } else {
            throw Exception("Failed to fetch symbols")
        }
    }

    override fun getLatestRates(base: CurrencyCode?, symbols: String?): Flow<Map<CurrencyCode, CurrencyExchangePair>> = flow {
        val response = apiService.getLatestRates(base, symbols)
        if (response.success) {
            val map = response.rates.mapValues {
                it.key; CurrencyExchangePair(
                    from = base ?: response.base,
                    to = it.key,
                    rate = it.value,
                )
            }
            emit(map)
        } else {
            throw Exception("Failed to fetch latest rates")
        }
    }
}
