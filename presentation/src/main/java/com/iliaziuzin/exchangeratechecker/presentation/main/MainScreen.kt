package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iliaziuzin.exchangeratechecker.presentation.R
import com.iliaziuzin.exchangeratechecker.ui.theme.ExchangeRateCheckerTheme

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var tabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("Currencies", "Favorites")
    val navController = rememberNavController()

    ExchangeRateCheckerTheme {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    tabs.forEachIndexed { index, title ->
                        val selected = tabIndex == index
                        NavigationBarItem(
                            selected = selected,
                            onClick = { tabIndex = index },
                            label = { Text(title) },
                            icon = {
                                val icon = when (index) {
                                    0 -> if (selected) R.drawable.icon_currencies_on else R.drawable.icon_currencies_off
                                    1 -> if (selected) R.drawable.icon_favorites_on else R.drawable.icon_favorites_off
                                    else -> R.drawable.icon_favorites_off
                                }
                                Icon(painterResource(id = icon), contentDescription = title)
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                when (tabIndex) {
                    0 -> {
                        NavHost(navController = navController, startDestination = "currencies") {
                            composable("currencies") {
                                CurrenciesScreen(
                                    uiState = uiState,
                                    onFavoriteClick = viewModel::addFavorite,
                                    onSortClick = { navController.navigate("filters") }
                                )
                            }
                            composable("filters") {
                                FiltersScreen(
                                    currentSortOption = uiState.sortOption,
                                    onSortOptionChange = viewModel::onSortOptionChange,
                                    onApplyClick = { navController.popBackStack() },
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                        }
                    }

                    1 -> FavoritesScreen(uiState = uiState, onRemoveFavorite = viewModel::removeFavorite)
                }
            }
        }
    }


}
