package com.iliaziuzin.exchangeratechecker.domain.usecase

import com.iliaziuzin.exchangeratechecker.data.remote.CurrencyCode
import com.iliaziuzin.exchangeratechecker.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetLatestRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(
        base: CurrencyCode? = null,
        symbols: String? = null
    ) = repository.getLatestRates(base, symbols)
}
