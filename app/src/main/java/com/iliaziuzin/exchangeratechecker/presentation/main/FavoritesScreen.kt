package com.iliaziuzin.exchangeratechecker.presentation.main

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
import com.iliaziuzin.exchangeratechecker.data.local.FavoriteCurrencyPairEntity

@Composable
fun FavoritesScreen(uiState: MainUiState, onRemoveFavorite: (FavoriteCurrencyPairEntity) -> Unit) {
    if (uiState.favorites.isEmpty()) {
        Text("No favorites yet.")
    } else {
        LazyColumn {
            items(uiState.favorites) { pair ->
                Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Text(text = "${pair.from} / ${pair.to}", modifier = Modifier.weight(1f))
                    Button(onClick = { onRemoveFavorite(pair) }) {
                        Text("Remove")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen(
        uiState = MainUiState(
            favorites = listOf(
                FavoriteCurrencyPairEntity("USD", "EUR"),
                FavoriteCurrencyPairEntity("GBP", "JPY")
            )
        ),
        onRemoveFavorite = {}
    )
}
