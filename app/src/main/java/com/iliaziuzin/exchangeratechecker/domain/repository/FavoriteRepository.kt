package com.iliaziuzin.exchangeratechecker.domain.repository

import com.iliaziuzin.exchangeratechecker.data.local.FavoriteCurrencyPair
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(): Flow<List<FavoriteCurrencyPair>>
    suspend fun addFavorite(pair: FavoriteCurrencyPair)
    suspend fun removeFavorite(pair: FavoriteCurrencyPair)
}
