package com.iliaziuzin.exchangeratechecker.domain.usecase

import com.iliaziuzin.exchangeratechecker.domain.models.FavoriteCurrenciesPair
import com.iliaziuzin.exchangeratechecker.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<FavoriteCurrenciesPair>> = repository.getFavorites()
}
