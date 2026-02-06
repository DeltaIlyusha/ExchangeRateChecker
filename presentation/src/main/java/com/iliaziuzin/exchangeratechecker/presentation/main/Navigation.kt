package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog

object AppDestinations {
    const val CURRENCIES_ROUTE = "currencies"
    const val FAVORITES_ROUTE = "favorites"
    const val FILTERS_ROUTE = "filters"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.CURRENCIES_ROUTE,
        modifier = modifier
    ) {
        composable(AppDestinations.CURRENCIES_ROUTE) {
            CurrenciesScreen(
                uiState = uiState,
                onFavoriteClick = viewModel::toggleFavorite,
                onCurrencySelected = viewModel::onCurrencySelected,
                onSortClick = { navController.navigate(AppDestinations.FILTERS_ROUTE) }
            )
        }
        dialog(
            route = AppDestinations.FILTERS_ROUTE,
            dialogProperties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false
            )
        ) {
            FiltersScreen(
                currentSortOption = uiState.sortOption,
                onSortOptionChange = viewModel::onSortOptionChange,
                onApplyClick = { navController.popBackStack() },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(AppDestinations.FAVORITES_ROUTE) {
            FavoritesScreen(uiState = uiState, onRemoveFavorite = viewModel::removeFavorite)
        }
    }
}
