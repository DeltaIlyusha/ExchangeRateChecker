package com.iliaziuzin.exchangeratechecker.domain.usecase

import com.iliaziuzin.exchangeratechecker.domain.repository.ExchangeRateRepository
import javax.inject.Inject


class GetFavoritesWithLatestRates @Inject constructor(
    private val repository: ExchangeRateRepository
) {
    operator fun invoke() = repository.getRatesForFavorites()
}