package com.iliaziuzin.exchangeratechecker.domain.repository

import com.iliaziuzin.exchangeratechecker.data.local.FavoriteCurrencyPairEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(): Flow<List<FavoriteCurrencyPairEntity>>
    suspend fun addFavorite(pair: FavoriteCurrencyPairEntity)
    suspend fun removeFavorite(pair: FavoriteCurrencyPairEntity)
}
