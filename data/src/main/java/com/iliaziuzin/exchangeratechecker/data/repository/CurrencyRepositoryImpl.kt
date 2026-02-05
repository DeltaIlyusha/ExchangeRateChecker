package com.iliaziuzin.exchangeratechecker.data.repository

import com.iliaziuzin.exchangeratechecker.data.remote.ApiService
import com.iliaziuzin.exchangeratechecker.domain.models.CurrencyCode
import com.iliaziuzin.exchangeratechecker.domain.models.ExchangeRate
import com.iliaziuzin.exchangeratechecker.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CurrencyRepository {

    override suspend fun getSymbols(): Flow<Map<CurrencyCode, String>> = flow {
        emit(apiService.getSymbols())
    }

    override suspend fun getLatestRates(base: CurrencyCode?, symbols: String?): Flow<Map<CurrencyCode, ExchangeRate>> = flow {
        emit(apiService.getLatestRates(base, symbols))
    }
}
