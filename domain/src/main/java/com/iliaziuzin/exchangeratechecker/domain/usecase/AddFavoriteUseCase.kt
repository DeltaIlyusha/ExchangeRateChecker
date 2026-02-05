package com.iliaziuzin.exchangeratechecker.domain.usecase

import com.iliaziuzin.exchangeratechecker.domain.models.FavoriteCurrenciesPair
import com.iliaziuzin.exchangeratechecker.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(pair: FavoriteCurrenciesPair) = repository.addFavorite(pair)
}
