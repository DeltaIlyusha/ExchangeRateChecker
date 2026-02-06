package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iliaziuzin.exchangeratechecker.models.UiCurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.presentation.main.composable.CurrencyComposable

@Composable
fun FavoritesScreen(uiState: MainUiState, onRemoveFavorite: (UiCurrencyExchangePair) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Favorites",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if (uiState.favorites.isEmpty()) {
            Text("No favorites yet.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.favorites, key = { it.from + it.to }) {
                    item ->
                    CurrencyComposable(
                        code = "${item.from}/${item.to}",
                        rate = item.rate,
                        isFavorite = item.isFavorite,
                        onFavoriteClick = { onRemoveFavorite(item) }
                    )
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
                UiCurrencyExchangePair("USD", "RUB", "1.0", true),
                UiCurrencyExchangePair("EUR", "USD", "1.0", true)
            )
        ),
        onRemoveFavorite = {}
    )
}
