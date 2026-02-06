package com.iliaziuzin.exchangeratechecker.di

import com.iliaziuzin.exchangeratechecker.repository.FavoriteRepositoryImpl
import com.iliaziuzin.exchangeratechecker.domain.repository.FavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteRepositoryModule {

    @Binds
    abstract fun bindFavoriteRepository(
        favoriteRepository: FavoriteRepositoryImpl
    ): FavoriteRepository
}