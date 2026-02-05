package com.iliaziuzin.exchangeratechecker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCurrencyPairEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCurrencyPairDao(): FavoriteCurrencyPairDao
}
