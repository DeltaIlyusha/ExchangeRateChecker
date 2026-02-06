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

    private var lastUpdated: Long = 0
    private var cachedRates: Map<CurrencyCode, CurrencyExchangePair>? = null
    private var cachedBase: String = ""
    private var cachedSymbols: String = ""

    override fun getSymbols(): Flow<Map<CurrencyCode, String>> = flow {
        val response = apiService.getSymbols()
        if (response.success) {
            emit(response.symbols)
        } else {
            throw Exception("Failed to fetch symbols")
        }
    }


    override fun getLatestRates(base: String?, symbols: String?): Flow<Map<CurrencyCode, CurrencyExchangePair>> = flow {
        val currentTime = System.currentTimeMillis()
        if (cachedRates != null && cachedBase == base && cachedSymbols == symbols && currentTime - lastUpdated < TimeUnit.MINUTES.toMillis(5)) {
            emit(cachedRates!!)
            return@flow
        }

        val response = apiService.getLatestRates(base, symbols)
        if (response.success) {
            val map = response.rates.mapValues {
                CurrencyExchangePair(
                    from = base ?: response.base,
                    to = it.key,
                    rate = it.value,
                )
            }
            cachedRates = map
            lastUpdated = currentTime
            emit(map)
        } else {
            throw Exception("Failed to fetch latest rates")
        }
    }
}
