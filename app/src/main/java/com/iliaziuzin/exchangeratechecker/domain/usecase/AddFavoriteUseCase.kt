package com.iliaziuzin.exchangeratechecker.domain.usecase

import com.iliaziuzin.exchangeratechecker.data.local.FavoriteCurrencyPairEntity
import com.iliaziuzin.exchangeratechecker.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(pair: FavoriteCurrencyPairEntity) = repository.addFavorite(pair)
}
