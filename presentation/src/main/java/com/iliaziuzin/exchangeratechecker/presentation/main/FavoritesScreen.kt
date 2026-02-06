package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iliaziuzin.exchangeratechecker.models.UiCurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.presentation.main.composable.CurrencyComposable
import com.iliaziuzin.exchangeratechecker.presentation.main.composable.SimpleScreenHeader
import com.iliaziuzin.exchangeratechecker.ui.theme.BackgroundDefault
import com.iliaziuzin.exchangeratechecker.ui.theme.Primary
import java.text.DecimalFormat

@Composable
fun FavoritesScreen(modifier:Modifier = Modifier, uiState: MainUiState, onRemoveFavorite: (UiCurrencyExchangePair) -> Unit) {
    Column(modifier = modifier.fillMaxSize()) {
        SimpleScreenHeader(
            title = "Favorites"
        )
        if (uiState.isLoading) {
            AnimatedVisibility(
                modifier = Modifier
                    .fillMaxSize(),
                visible = true,
                enter = EnterTransition.None,
                exit = fadeOut(),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.BackgroundDefault)
                        .wrapContentSize(),
                    trackColor = MaterialTheme.colorScheme.Primary,)
            }
        }
        else if (uiState.favorites.isEmpty()) {
            Text("No favorites yet.")
        } else {
            LazyColumn(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.BackgroundDefault).weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.favorites, key = { it.key }) {
                    item ->
                    val decimalFormat = DecimalFormat("#.######")
                    CurrencyComposable(
                        code = "${item.from}/${item.to}",
                        rate = decimalFormat.format(item.rate),
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
                UiCurrencyExchangePair("USD", "RUB", 1.0, true, "USDRUB"),
                UiCurrencyExchangePair("EUR", "USD", 1.0, true, "EURUSD")
            )
        ),
        onRemoveFavorite = {}
    )
}
