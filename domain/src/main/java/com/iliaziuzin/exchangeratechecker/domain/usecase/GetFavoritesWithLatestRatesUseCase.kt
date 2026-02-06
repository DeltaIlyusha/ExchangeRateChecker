package com.iliaziuzin.exchangeratechecker.domain.usecase

import com.iliaziuzin.exchangeratechecker.domain.repository.ExchangeRateRepository
import javax.inject.Inject


class GetFavoritesWithLatestRatesUseCase @Inject constructor(
    private val repository: ExchangeRateRepository
) {
    operator fun invoke() = repository.getRatesForFavorites()
}