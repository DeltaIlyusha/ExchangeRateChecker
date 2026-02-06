package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iliaziuzin.exchangeratechecker.models.UiCurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.presentation.R
import com.iliaziuzin.exchangeratechecker.presentation.main.composable.CurrencyComposable
import com.iliaziuzin.exchangeratechecker.ui.theme.BorderStroke

@Composable
fun CurrenciesScreen(
    modifier: Modifier = Modifier,
    uiState: MainUiState,
    onFavoriteClick: (UiCurrencyExchangePair) -> Unit,
    onSortClick: () -> Unit,
) {
    Scaffold(modifier = modifier) { paddingValues ->
        if (uiState.isLoading) {
            Text("Loading...")
        } else if (uiState.error != null) {
            Text(text = "Error: ${uiState.error}")
        } else {
            Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
                Text(
                    text = "Currencies",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = "EUR",
                        onValueChange = {},
                        modifier = Modifier.weight(1f),
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_arrow_down),
                                contentDescription = "Select currency"
                            )
                        }
                    )
                    OutlinedIconButton(
                        onClick = onSortClick,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.BorderStroke)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_filters),
                            contentDescription = "Sort currencies"
                        )
                    }
                }

                Spacer(modifier = Modifier.size(16.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    val sortedSymbols = when (uiState.sortOption) {
                        SortOption.CODE_AZ -> uiState.currencyExchangePairs.sortedBy { it.to }
                        SortOption.CODE_ZA -> uiState.currencyExchangePairs.sortedByDescending { it.to }
                        SortOption.QUOTE_ASC -> uiState.currencyExchangePairs.sortedBy { it.rate }
                        SortOption.QUOTE_DESC -> uiState.currencyExchangePairs.sortedByDescending { it.rate }
                    }

                    items(items = sortedSymbols, key = { it.to }) { it ->
                        CurrencyComposable(
                            code = it.to,
                            rate = it.rate,
                            isFavorite = it.isFavorite,
                            onFavoriteClick = { onFavoriteClick(it) })
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
                UiCurrencyExchangePair("USD", "RUB", "1.0", true),
                UiCurrencyExchangePair("EUR", "RUB", "1.0", false),
                UiCurrencyExchangePair("RUB", "USD", "1.0", false),
            ),
        ),
        onFavoriteClick = {},
        onSortClick = {}
    )
}
