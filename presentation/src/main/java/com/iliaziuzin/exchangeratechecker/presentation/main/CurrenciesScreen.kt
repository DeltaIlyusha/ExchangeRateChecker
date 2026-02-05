package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iliaziuzin.exchangeratechecker.models.CurrencyExchangePairItem
import com.iliaziuzin.exchangeratechecker.presentation.main.composable.CurrencyComposable

@Composable
fun CurrenciesScreen(
    modifier: Modifier = Modifier,
    uiState: MainUiState,
    onFavoriteClick: (CurrencyExchangePairItem) -> Unit,
    onSortClick: () -> Unit,
) {
    Scaffold(modifier = modifier) { paddingValues ->
        if (uiState.isLoading) {
            Text("Loading...")
        } else if (uiState.error != null) {
            Text(text = "Error: ${uiState.error}")
        } else {
            Column {
                Button(onClick = onSortClick, modifier = Modifier.padding(8.dp)) {
                    Text("Sort")
                }
                LazyColumn {
                    val sortedSymbols = when (uiState.sortOption) {
                        SortOption.NAME_ASC -> uiState.currencyExchangePairs.sortedBy { it.to }
                        SortOption.NAME_DESC -> uiState.currencyExchangePairs.sortedByDescending { it.to }
                        SortOption.RATE_ASC -> uiState.currencyExchangePairs.sortedBy { it.rate }
                        SortOption.RATE_DESC -> uiState.currencyExchangePairs.sortedByDescending { it.rate }
                    }

                    items(items = sortedSymbols, key = { it.to }) { (code, name) ->
                        CurrencyComposable(code = code, rate = "0.0", isFavorite = false, onFavoriteClick = {})
                    }
                }
            }
        }
    }



}

@Preview(showBackground = true)
@Composable
fun CurrenciesScreenPreview() {
    CurrenciesScreen(
        uiState = MainUiState(
            currencyExchangePairs = listOf(
                CurrencyExchangePairItem("USD", "RUB", 1.0),
                CurrencyExchangePairItem("EUR", "RUB", 1.0),
                CurrencyExchangePairItem("RUB", "USD", 1.0),
            ),
        ),
        onFavoriteClick = {},
        onSortClick = {}
    )
}
