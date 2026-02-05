package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iliaziuzin.exchangeratechecker.data.local.FavoriteCurrencyPair

@Composable
fun CurrenciesScreen(
    uiState: MainUiState,
    onAddFavorite: (FavoriteCurrencyPair) -> Unit,
    onSortClick: () -> Unit
) {
    if (uiState.isLoadingSymbols || uiState.isLoadingRates) {
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
                    SortOption.NAME_ASC -> uiState.symbols.toList().sortedBy { it.second }
                    SortOption.NAME_DESC -> uiState.symbols.toList().sortedByDescending { it.second }
                    SortOption.RATE_ASC -> uiState.symbols.toList().sortedBy { uiState.rates[it.first] ?: Double.MAX_VALUE }
                    SortOption.RATE_DESC -> uiState.symbols.toList().sortedByDescending { uiState.rates[it.first] ?: Double.MIN_VALUE }
                }

                items(sortedSymbols) { (code, name) ->
                    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "$code: $name")
                            uiState.rates[code]?.let {
                                Text(text = "Rate: $it")
                            }
                        }
                        Button(onClick = { /*TODO: Implement logic to add to favorites*/ }) {
                            Text("Add to Favorites")
                        }
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
            symbols = mapOf(
                "USD" to "United States Dollar",
                "EUR" to "Euro",
                "JPY" to "Japanese Yen"
            ),
            rates = mapOf(
                "USD" to 1.0,
                "EUR" to 0.9,
                "JPY" to 130.0
            )
        ),
        onAddFavorite = {},
        onSortClick = {}
    )
}
