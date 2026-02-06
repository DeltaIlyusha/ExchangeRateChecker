package com.iliaziuzin.exchangeratechecker.repository

import com.iliaziuzin.exchangeratechecker.local.FavoriteCurrencyPairDao
import com.iliaziuzin.exchangeratechecker.toFavoriteCurrenciesPair
import com.iliaziuzin.exchangeratechecker.toFavoriteCurrencyPairEntity
import com.iliaziuzin.exchangeratechecker.domain.models.FavoriteCurrenciesPair
import com.iliaziuzin.exchangeratechecker.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteCurrencyPairDao
) : FavoriteRepository {

    override fun getFavorites(): Flow<List<FavoriteCurrenciesPair>> {
        return dao.getAll()
            .flowOn(Dispatchers.IO)
            .map { list ->
                list.map { entity -> entity.toFavoriteCurrenciesPair() }
            }
    }

    override suspend fun addFavorite(pair: FavoriteCurrenciesPair) {
        withContext(Dispatchers.IO) {
            dao.insert(pair.toFavoriteCurrencyPairEntity())
        }
    }

    override suspend fun removeFavorite(pair: FavoriteCurrenciesPair) {
        withContext(Dispatchers.IO) {
            dao.delete(pair.toFavoriteCurrencyPairEntity())
        }
    }
}
