package com.iliaziuzin.exchangeratechecker.presentation.main

import com.iliaziuzin.exchangeratechecker.domain.usecase.RemoveFavoriteUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iliaziuzin.exchangeratechecker.domain.usecase.AddFavoriteUseCase
import com.iliaziuzin.exchangeratechecker.domain.usecase.GetCurrenciesUseCase
import com.iliaziuzin.exchangeratechecker.domain.usecase.GetFavoritesWithLatestRatesUseCase
import com.iliaziuzin.exchangeratechecker.domain.usecase.GetLatestRatesForCurrencyUseCase
import com.iliaziuzin.exchangeratechecker.mappers.toCurrencyExchangePairItem
import com.iliaziuzin.exchangeratechecker.mappers.toFavoriteCurrenciesPair
import com.iliaziuzin.exchangeratechecker.models.UiCurrencyExchangePair
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SortOption(val displayName: String) {
    CODE_AZ("Code A-Z"),
    CODE_ZA("Code Z-A"),
    QUOTE_ASC("Quote Asc."),
    QUOTE_DESC("Quote Desc.")
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val getFavoritesWithLatestRatesUseCase: GetFavoritesWithLatestRatesUseCase,
    private val getLatestRatesForCurrency: GetLatestRatesForCurrencyUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()


    init {
        loadAvailableCurrencies()
        loadRatesForCurrencies(_uiState.value.selectedCurrency)
        loadRatesForFavorites()
    }

    fun onSortOptionChange(sortOption: SortOption) {
        _uiState.update { it.copy(sortOption = sortOption) }
    }

    fun onCurrencySelected(currency: String) {
        _uiState.update { it.copy(selectedCurrency = currency, isLoading = true) }
        loadRatesForCurrencies(currency)
    }

    private fun loadAvailableCurrencies() {
        getCurrenciesUseCase().onEach { currencies ->
            _uiState.update {
                it.copy(
                    currencySymbols = currencies.keys.toList(),
                    isLoading = false
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun loadRatesForFavorites() {
        getFavoritesWithLatestRatesUseCase().onEach { favorites ->
            _uiState.update { it.copy(isLoading = false, favorites = favorites.map { favorite -> favorite.toCurrencyExchangePairItem() }) }
        }.launchIn(viewModelScope)
    }

    private fun loadRatesForCurrencies(baseCurrency:String) {
        getLatestRatesForCurrency(baseCurrency).onEach { currencies ->
            _uiState.update { it.copy(isLoading = false, currencyExchangePairs = currencies.map { currency -> currency.toCurrencyExchangePairItem() }) }
        }.launchIn(viewModelScope)
    }

    fun toggleFavorite(pair: UiCurrencyExchangePair) {
        if (pair.isFavorite) removeFavorite(pair)
        else addFavorite(pair)
    }

    private fun addFavorite(pair: UiCurrencyExchangePair) {
        viewModelScope.launch {
            addFavoriteUseCase(pair.toFavoriteCurrenciesPair())
        }
    }

    fun removeFavorite(pair: UiCurrencyExchangePair) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            removeFavoriteUseCase(pair.toFavoriteCurrenciesPair())
        }
    }
}

data class MainUiState(
    val isLoading: Boolean = true,
    val currencySymbols:List<String> = emptyList(),
    val selectedCurrency:String = "EUR",
    val currencyExchangePairs: List<UiCurrencyExchangePair> = emptyList(),
    val favorites: List<UiCurrencyExchangePair> = emptyList(),
    val error: String? = null,
    val sortOption: SortOption = SortOption.CODE_AZ
)

data class CurrenciesUiState(
    val isLoading: Boolean = false,
    val currencyExchangePairs: List<UiCurrencyExchangePair> = emptyList(),
    val sortOption: SortOption = SortOption.CODE_AZ
)
