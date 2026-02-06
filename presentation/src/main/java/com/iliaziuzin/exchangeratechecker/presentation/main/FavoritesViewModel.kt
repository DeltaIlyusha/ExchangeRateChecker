package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iliaziuzin.exchangeratechecker.domain.usecase.GetFavoritesWithLatestRatesUseCase
import com.iliaziuzin.exchangeratechecker.domain.usecase.RemoveFavoriteUseCase
import com.iliaziuzin.exchangeratechecker.mappers.toCurrencyExchangePairItem
import com.iliaziuzin.exchangeratechecker.mappers.toFavoriteCurrenciesPair
import com.iliaziuzin.exchangeratechecker.models.UiCurrencyExchangePair
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesWithLatestRatesUseCase: GetFavoritesWithLatestRatesUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState = _uiState.asStateFlow()

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    init {
        loadRatesForFavorites()
    }

    var loadRatesForFavoritesJob: Job? = null
    private fun loadRatesForFavorites() {
        loadRatesForFavoritesJob?.cancel()
        loadRatesForFavoritesJob = getFavoritesWithLatestRatesUseCase().onEach { favorites ->
            _uiState.update { it.copy(isLoading = false, favorites = favorites.map { favorite -> favorite.toCurrencyExchangePairItem() }) }
        }.catch { e ->
            _uiState.update { it.copy(isLoading = false) }
            e.message?.let { _errorFlow.emit(it) }
        }.launchIn(viewModelScope)
    }

    fun toggleFavorite(pair: UiCurrencyExchangePair) {
        _uiState.update { currentState ->
            val updatedFavPairs = currentState.favorites.toMutableList()
            if (pair.isFavorite) {
                updatedFavPairs.removeIf { it.key == pair.key }
            }

            currentState.copy(favorites = updatedFavPairs)
        }

        if (pair.isFavorite) {
            removeFavorite(pair)
        }
    }

    private fun removeFavorite(pair: UiCurrencyExchangePair) {
        viewModelScope.launch {
            removeFavoriteUseCase(pair.toFavoriteCurrenciesPair())
        }
    }
}

data class FavoritesUiState(
    val isLoading: Boolean = true,
    val favorites: List<UiCurrencyExchangePair> = emptyList(),
)
