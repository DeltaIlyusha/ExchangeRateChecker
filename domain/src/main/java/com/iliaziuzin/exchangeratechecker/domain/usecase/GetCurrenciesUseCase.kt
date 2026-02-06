package com.iliaziuzin.exchangeratechecker.domain.usecase

import com.iliaziuzin.exchangeratechecker.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke() = repository.getSymbols()
}