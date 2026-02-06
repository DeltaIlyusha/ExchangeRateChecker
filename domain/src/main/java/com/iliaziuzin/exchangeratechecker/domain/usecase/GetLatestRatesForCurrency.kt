package com.iliaziuzin.exchangeratechecker.domain.usecase

import com.iliaziuzin.exchangeratechecker.domain.repository.ExchangeRateRepository
import javax.inject.Inject


class GetLatestRatesForCurrency @Inject constructor(
    private val repository: ExchangeRateRepository
) {
    operator fun invoke(base: String = "USD") = repository.getLatestRatesForCurrency(base)
}