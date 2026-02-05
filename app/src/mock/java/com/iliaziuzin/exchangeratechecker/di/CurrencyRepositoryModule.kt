package com.iliaziuzin.exchangeratechecker.di

import com.iliaziuzin.exchangeratechecker.data.repository.FakeCurrencyRepository
import com.iliaziuzin.exchangeratechecker.domain.repository.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrencyRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCurrencyRepository(
        fakeCurrencyRepository: FakeCurrencyRepository
    ): CurrencyRepository
}
