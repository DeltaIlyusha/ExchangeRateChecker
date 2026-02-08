package com.iliaziuzin.exchangeratechecker.presentation.main.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iliaziuzin.exchangeratechecker.domain.usecase.AddFavoriteUseCase
import com.iliaziuzin.exchangeratechecker.domain.usecase.GetCurrenciesUseCase
import com.iliaziuzin.exchangeratechecker.domain.usecase.GetLatestRatesForCurrencyUseCase
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

enum class SortOption(val displayName: String) {
    CODE_AZ("Code A-Z"),
    CODE_ZA("Code Z-A"),
    QUOTE_ASC("Quote Asc."),
    QUOTE_DESC("Quote Desc.")
}

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val getLatestRatesForCurrency: GetLatestRatesForCurrencyUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CurrenciesUiState())
    val uiState = _uiState.asStateFlow()

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    init {
        loadAvailableCurrencies()
        loadRatesForCurrencies(_uiState.value.selectedCurrency)
    }

    fun onSortOptionChange(sortOption: SortOption) {
        _uiState.update {
            val sortedPairs = sortList(it.currencyExchangePairs, sortOption)
            it.copy(sortOption = sortOption, currencyExchangePairs = sortedPairs)
        }
    }

    fun onCurrencySelected(currency: String) {
        if (currency == _uiState.value.selectedCurrency) return
        _uiState.update { it.copy(selectedCurrency = currency, isLoading = true) }
        loadRatesForCurrencies(currency)
    }

    var loadAvailableCurrencies: Job? = null
    private fun loadAvailableCurrencies() {
        loadAvailableCurrencies?.cancel()
        loadAvailableCurrencies = getCurrenciesUseCase().onEach { currencies ->
            _uiState.update {
                it.copy(
                    currencySymbols = currencies.keys.toList(),
                )
            }
        }.catch { e ->
            _uiState.update { it.copy(isLoading = false) }
            e.message?.let { _errorFlow.emit(it) }
        }.launchIn(viewModelScope)
    }

    var loadRatesForCurrenciesJob: Job? = null

    private fun loadRatesForCurrencies(baseCurrency: String) {
        loadRatesForCurrenciesJob?.cancel()
        loadRatesForCurrenciesJob = getLatestRatesForCurrency(baseCurrency).onEach { currencies ->
            _uiState.update { currentState ->
                val unsortedList = currencies.map { currency -> currency.toCurrencyExchangePairItem() }
                val sortedList = sortList(unsortedList, currentState.sortOption)
                currentState.copy(isLoading = false, currencyExchangePairs = sortedList)
            }
        }.catch { e ->
            _uiState.update { it.copy(isLoading = false) }
            e.message?.let { _errorFlow.emit(it) }
        }.launchIn(viewModelScope)
    }

    private fun sortList(list: List<UiCurrencyExchangePair>, option: SortOption): List<UiCurrencyExchangePair> {
        return when (option) {
            SortOption.CODE_AZ -> list.sortedBy { it.to }
            SortOption.CODE_ZA -> list.sortedByDescending { it.to }
            SortOption.QUOTE_ASC -> list.sortedBy { it.rate }
            SortOption.QUOTE_DESC -> list.sortedByDescending { it.rate }
        }
    }

    fun toggleFavorite(pair: UiCurrencyExchangePair) {
        _uiState.update { currentState ->
            val updatedPairs = currentState.currencyExchangePairs.map {
                if (it.key == pair.key) {
                    it.copy(isFavorite = !it.isFavorite)
                } else {
                    it
                }
            }
            currentState.copy(currencyExchangePairs = updatedPairs)
        }

        if (pair.isFavorite) {
            removeFavorite(pair)
        } else {
            addFavorite(pair)
        }
    }

    private fun addFavorite(pair: UiCurrencyExchangePair) {
        viewModelScope.launch {
            addFavoriteUseCase(pair.toFavoriteCurrenciesPair())
        }
    }

    private fun removeFavorite(pair: UiCurrencyExchangePair) {
        viewModelScope.launch {
            removeFavoriteUseCase(pair.toFavoriteCurrenciesPair())
        }
    }
}

data class CurrenciesUiState(
    val isLoading: Boolean = true,
    val currencySymbols:List<String> = emptyList(),
    val selectedCurrency:String = "EUR",
    val currencyExchangePairs: List<UiCurrencyExchangePair> = emptyList(),
    val sortOption: SortOption = SortOption.CODE_AZ
)