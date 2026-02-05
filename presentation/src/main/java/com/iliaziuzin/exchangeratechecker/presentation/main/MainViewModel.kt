package com.iliaziuzin.exchangeratechecker.presentation.main

import com.iliaziuzin.exchangeratechecker.domain.usecase.RemoveFavoriteUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iliaziuzin.exchangeratechecker.domain.usecase.AddFavoriteUseCase
import com.iliaziuzin.exchangeratechecker.domain.usecase.GetFavoritesWithLatestRates
import com.iliaziuzin.exchangeratechecker.mappers.toCurrencyExchangePairItem
import com.iliaziuzin.exchangeratechecker.mappers.toFavoriteCurrenciesPair
import com.iliaziuzin.exchangeratechecker.models.CurrencyExchangePairItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SortOption {
    NAME_ASC,
    NAME_DESC,
    RATE_ASC,
    RATE_DESC
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFavoritesWithLatestRates: GetFavoritesWithLatestRates,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadRatesForCurrencies()
        loadRatesForFavorites()
    }

    fun onSortOptionChange(sortOption: SortOption) {
        _uiState.update { it.copy(sortOption = sortOption) }
    }

    private fun loadRatesForFavorites() {
        getFavoritesWithLatestRates().onEach { favorites ->
            _uiState.update { it.copy(favorites = favorites.map { favorite -> favorite.toCurrencyExchangePairItem() }) }
        }.launchIn(viewModelScope)
    }

    private fun loadRatesForCurrencies() {

    }

    /*private fun loadSymbols() {
        *//*viewModelScope.launch {
            _uiState.update { it.copy(isLoadingSymbols = true) }
            try {
                val symbols = getSymbolsUseCase()
                _uiState.update { it.copy(symbols = symbols, isLoadingSymbols = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoadingSymbols = false) }
            }
        }*//*
    }*/

    /*private fun loadLatestRates() {
        *//*viewModelScope.launch {
            _uiState.update { it.copy(isLoadingRates = true) }
            try {
                val rates = getLatestRatesUseCase()
                _uiState.update { it.copy(rates = rates.rates, isLoadingRates = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoadingRates = false) }
            }
        }*//*
    }*/

    /*private fun loadFavorites() {
        getFavoritesUseCase().onEach { favorites ->
            _uiState.update { it.copy(favorites = favorites.map { favorite -> favorite.toCurrencyExchangePairItem() }) }
        }.launchIn(viewModelScope)
    }*/

    fun addFavorite(pair: CurrencyExchangePairItem) {
        viewModelScope.launch {
            addFavoriteUseCase(pair.toFavoriteCurrenciesPair())
        }
    }

    fun removeFavorite(pair: CurrencyExchangePairItem) {
        viewModelScope.launch {
            removeFavoriteUseCase(pair.toFavoriteCurrenciesPair())
        }
    }
}

data class MainUiState(
    val isLoading: Boolean = false,
    val currencyExchangePairs: List<CurrencyExchangePairItem> = emptyList(),
    val favorites: List<CurrencyExchangePairItem> = emptyList(),
    val error: String? = null,
    val sortOption: SortOption = SortOption.NAME_ASC
)
