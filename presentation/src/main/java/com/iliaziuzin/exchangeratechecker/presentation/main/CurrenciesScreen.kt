package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iliaziuzin.exchangeratechecker.models.UiCurrencyExchangePair
import com.iliaziuzin.exchangeratechecker.presentation.R
import com.iliaziuzin.exchangeratechecker.presentation.main.composable.CurrencyComposable
import com.iliaziuzin.exchangeratechecker.ui.theme.BackgroundDefault
import com.iliaziuzin.exchangeratechecker.ui.theme.BackgroundHeader
import com.iliaziuzin.exchangeratechecker.ui.theme.Primary
import com.iliaziuzin.exchangeratechecker.ui.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesScreen(
    modifier: Modifier = Modifier,
    uiState: MainUiState,
    onFavoriteClick: (UiCurrencyExchangePair) -> Unit,
    onSortClick: () -> Unit,
    onCurrencySelected: (String) -> Unit
) {
    Scaffold(modifier = modifier.background(MaterialTheme.colorScheme.BackgroundDefault)) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize()) {
            CurrenciesHeader(
                modifier = Modifier.padding(paddingValues),
                selectedCurrency = uiState.selectedCurrency,
                currencySymbols = uiState.currencySymbols,
                onSortClick = onSortClick,
                onCurrencySelected = onCurrencySelected
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
                            .wrapContentSize()
                    )
                }
            } else if (uiState.error != null) {
                Text(text = "Error: ${uiState.error}")
            } else {
                LazyColumn(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesHeader(modifier: Modifier = Modifier,
                     selectedCurrency:String,
                     currencySymbols:List<String>,
                     onSortClick: () -> Unit,
                     onCurrencySelected: (String) -> Unit
                     ) {
        Box(
            Modifier.background(MaterialTheme.colorScheme.BackgroundHeader)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Currencies",
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 42.dp, bottom = 16.dp),
                    style = MaterialTheme.typography.titleLarge
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    var expanded by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = selectedCurrency,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    tint = MaterialTheme.colorScheme.Primary,
                                    painter = painterResource(if (!expanded) R.drawable.icon_arrow_down else R.drawable.icon_arrow_up),
                                    contentDescription = "Currencies dropdown",
                                )},
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.Secondary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.Secondary,
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.Secondary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            currencySymbols.forEach { symbol ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            modifier = Modifier.padding(start = 16.dp, end = 24.dp, top = 18.dp, bottom = 18.dp),
                                            text = symbol,
                                            style = MaterialTheme.typography.bodySmall,
                                        )
                                    },
                                    onClick = {
                                        onCurrencySelected(symbol)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    OutlinedIconButton(
                        modifier = Modifier.size(48.dp),
                        onClick = onSortClick,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.Secondary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_filters),
                            contentDescription = "Sort currencies",
                            tint = MaterialTheme.colorScheme.Primary
                        )
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.fillMaxWidth().size(1.dp).background(color = MaterialTheme.colorScheme.Secondary))
            }

        }
}

@Preview(showBackground = true)
@Composable
fun CurrenciesScreenPreview() {
    CurrenciesScreen(
        uiState = MainUiState(
            isLoading = true,
            currencySymbols = listOf("USD", "EUR", "RUB"),
            currencyExchangePairs = listOf(
                UiCurrencyExchangePair("USD", "RUB", "1.0", true),
                UiCurrencyExchangePair("EUR", "RUB", "1.0", false),
                UiCurrencyExchangePair("RUB", "USD", "1.0", false),
            ),
        ),
        onFavoriteClick = {},
        onSortClick = {},
        onCurrencySelected = {}
    )
}
