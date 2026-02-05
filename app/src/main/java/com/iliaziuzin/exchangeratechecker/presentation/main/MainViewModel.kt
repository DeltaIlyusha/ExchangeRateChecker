package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iliaziuzin.exchangeratechecker.data.local.FavoriteCurrencyPairEntity
import com.iliaziuzin.exchangeratechecker.domain.usecase.AddFavoriteUseCase
import com.iliaziuzin.exchangeratechecker.domain.usecase.GetFavoritesUseCase
import com.iliaziuzin.exchangeratechecker.domain.usecase.GetLatestRatesUseCase
import com.iliaziuzin.exchangeratechecker.domain.usecase.GetSymbolsUseCase
import com.iliaziuzin.exchangeratechecker.domain.usecase.RemoveFavoriteUseCase
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
    private val getSymbolsUseCase: GetSymbolsUseCase,
    private val getLatestRatesUseCase: GetLatestRatesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadSymbols()
        loadLatestRates()
        loadFavorites()
    }

    fun onSortOptionChange(sortOption: SortOption) {
        _uiState.update { it.copy(sortOption = sortOption) }
    }

    private fun loadSymbols() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingSymbols = true) }
            try {
                val symbols = getSymbolsUseCase()
                _uiState.update { it.copy(symbols = symbols.symbols, isLoadingSymbols = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoadingSymbols = false) }
            }
        }
    }

    private fun loadLatestRates() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingRates = true) }
            try {
                val rates = getLatestRatesUseCase()
                _uiState.update { it.copy(rates = rates.rates, isLoadingRates = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoadingRates = false) }
            }
        }
    }

    private fun loadFavorites() {
        getFavoritesUseCase().onEach { favorites ->
            _uiState.update { it.copy(favorites = favorites) }
        }.launchIn(viewModelScope)
    }

    fun addFavorite(pair: FavoriteCurrencyPairEntity) {
        viewModelScope.launch {
            addFavoriteUseCase(pair)
        }
    }

    fun removeFavorite(pair: FavoriteCurrencyPairEntity) {
        viewModelScope.launch {
            removeFavoriteUseCase(pair)
        }
    }
}

data class MainUiState(
    val isLoadingSymbols: Boolean = false,
    val isLoadingRates: Boolean = false,
    val symbols: Map<String, String> = emptyMap(),
    val rates: Map<String, Double> = emptyMap(),
    val favorites: List<FavoriteCurrencyPairEntity> = emptyList(),
    val error: String? = null,
    val sortOption: SortOption = SortOption.NAME_ASC
)
