package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iliaziuzin.exchangeratechecker.presentation.R
import com.iliaziuzin.exchangeratechecker.ui.theme.ExchangeRateCheckerTheme

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val tabs = listOf("Currencies" to "currencies", "Favorites" to "favorites")

    val showBottomBar = tabs.any { it.second == currentRoute }

    ExchangeRateCheckerTheme {
        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    NavigationBar {
                        tabs.forEachIndexed { index, (title, route) ->
                            val selected = currentRoute == route
                            NavigationBarItem(
                                selected = selected,
                                onClick = {
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
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
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "currencies",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("currencies") {
                    CurrenciesScreen(
                        uiState = uiState,
                        onFavoriteClick = viewModel::addFavorite,
                        onCurrencySelected = viewModel::onCurrencySelected,
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
                composable("favorites") {
                    FavoritesScreen(uiState = uiState, onRemoveFavorite = viewModel::removeFavorite)
                }
            }
        }
    }
}
