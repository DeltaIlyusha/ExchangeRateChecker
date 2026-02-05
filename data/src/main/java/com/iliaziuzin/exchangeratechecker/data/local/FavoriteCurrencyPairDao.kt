package com.iliaziuzin.exchangeratechecker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCurrencyPairDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pair: FavoriteCurrencyPairEntity)

    @Delete
    suspend fun delete(pair: FavoriteCurrencyPairEntity)

    @Query("SELECT * FROM favorite_currency_pairs ORDER BY `from` ASC, `to` ASC")
    fun getAll(): Flow<List<FavoriteCurrencyPairEntity>>
}
