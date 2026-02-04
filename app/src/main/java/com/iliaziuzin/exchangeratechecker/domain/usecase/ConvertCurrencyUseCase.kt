package com.iliaziuzin.exchangeratechecker.domain.usecase

import com.iliaziuzin.exchangeratechecker.data.remote.CurrencyCode
import com.iliaziuzin.exchangeratechecker.domain.repository.CurrencyRepository
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(
        amount: String,
        from: CurrencyCode,
        to: CurrencyCode,
        date: String? = null
    ) = repository.convert(amount, from, to, date)
}
