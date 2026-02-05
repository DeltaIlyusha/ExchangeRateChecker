package com.iliaziuzin.exchangeratechecker.domain.repository

import com.iliaziuzin.exchangeratechecker.domain.models.FavoriteCurrenciesPair
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(): Flow<List<FavoriteCurrenciesPair>>
    suspend fun addFavorite(pair: FavoriteCurrenciesPair)
    suspend fun removeFavorite(pair: FavoriteCurrenciesPair)
}
