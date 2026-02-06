package com.iliaziuzin.exchangeratechecker.di

import android.content.Context
import androidx.room.Room
import com.iliaziuzin.exchangeratechecker.local.AppDatabase
import com.iliaziuzin.exchangeratechecker.local.FavoriteCurrencyPairDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "exchange_rate_checker_database"
        ).build()
    }

    @Provides
    fun provideFavoriteCurrencyPairDao(database: AppDatabase): FavoriteCurrencyPairDao {
        return database.favoriteCurrencyPairDao()
    }
}
