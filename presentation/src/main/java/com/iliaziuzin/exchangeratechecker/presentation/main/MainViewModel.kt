package com.iliaziuzin.exchangeratechecker.presentation.main

import com.iliaziuzin.exchangeratechecker.domain.usecase.RemoveFavoriteUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iliaziuzin.exchangeratechecker.domain.usecase.AddFavoriteUseCase
import com.iliaziuzin.exchangeratechecker.domain.usecase.GetFavoritesWithLatestRates
import com.iliaziuzin.exchangeratechecker.domain.usecase.GetLatestRatesForCurrency
import com.iliaziuzin.exchangeratechecker.mappers.toCurrencyExchangePairItem
import com.iliaziuzin.exchangeratechecker.mappers.toFavoriteCurrenciesPair
import com.iliaziuzin.exchangeratechecker.models.UiCurrencyExchangePair
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
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
    private val getLatestRatesForCurrency: GetLatestRatesForCurrency,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    /*val uiCurrencies: StateFlow<CurrenciesUiState> = getLatestRatesForCurrency("USD")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Keep the flow active for 5s after the UI is gone
            initialValue = CurrenciesUiState(isLoading = true, currencyExchangePairs = emptyList())
        )*/


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
        getLatestRatesForCurrency().onEach { currencies ->
            _uiState.update { it.copy(currencyExchangePairs = currencies.map { currency -> currency.toCurrencyExchangePairItem() }) }
        }.launchIn(viewModelScope)
    }


    fun addFavorite(pair: UiCurrencyExchangePair) {
        viewModelScope.launch {
            addFavoriteUseCase(pair.toFavoriteCurrenciesPair())
        }
    }

    fun removeFavorite(pair: UiCurrencyExchangePair) {
        viewModelScope.launch {
            removeFavoriteUseCase(pair.toFavoriteCurrenciesPair())
        }
    }
}

data class MainUiState(
    val isLoading: Boolean = false,
    val currencyExchangePairs: List<UiCurrencyExchangePair> = emptyList(),
    val favorites: List<UiCurrencyExchangePair> = emptyList(),
    val error: String? = null,
    val sortOption: SortOption = SortOption.NAME_ASC
)

data class CurrenciesUiState(
    val isLoading: Boolean = false,
    val currencyExchangePairs: List<UiCurrencyExchangePair> = emptyList(),
    val sortOption: SortOption = SortOption.NAME_ASC
)
