package com.iliaziuzin.exchangeratechecker.data.repository

import com.iliaziuzin.exchangeratechecker.data.local.FavoriteCurrencyPairEntity
import com.iliaziuzin.exchangeratechecker.data.local.FavoriteCurrencyPairDao
import com.iliaziuzin.exchangeratechecker.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteCurrencyPairDao
) : FavoriteRepository {

    override fun getFavorites(): Flow<List<FavoriteCurrencyPairEntity>> {
        return dao.getAll()
    }

    override suspend fun addFavorite(pair: FavoriteCurrencyPairEntity) {
        dao.insert(pair)
    }

    override suspend fun removeFavorite(pair: FavoriteCurrencyPairEntity) {
        dao.delete(pair)
    }
}
