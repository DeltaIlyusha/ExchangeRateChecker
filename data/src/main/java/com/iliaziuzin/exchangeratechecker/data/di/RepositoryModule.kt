package com.iliaziuzin.exchangeratechecker.data.di

import com.iliaziuzin.exchangeratechecker.data.repository.ExchangeRateRepositoryImpl
import com.iliaziuzin.exchangeratechecker.domain.repository.ExchangeRateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindExchangeRateRepository(
        exchangeRateRepositoryImpl: ExchangeRateRepositoryImpl
    ): ExchangeRateRepository
}
