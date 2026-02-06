package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import kotlinx.coroutines.flow.collectLatest

object AppDestinations {
    const val CURRENCIES_ROUTE = "currencies"
    const val FAVORITES_ROUTE = "favorites"
    const val FILTERS_ROUTE = "filters"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.CURRENCIES_ROUTE,
        modifier = modifier
    ) {
        composable(AppDestinations.CURRENCIES_ROUTE) {
            val viewModel: CurrenciesViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.errorFlow.collectLatest { error ->
                    snackbarHostState.showSnackbar(message = error, actionLabel = "Dismiss")
                }
            }

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
            val backStackEntry = remember(it) {
                navController.getBackStackEntry(AppDestinations.CURRENCIES_ROUTE)
            }
            val viewModel: CurrenciesViewModel = hiltViewModel(backStackEntry)
            val uiState by viewModel.uiState.collectAsState()
            FiltersScreen(
                currentSortOption = uiState.sortOption,
                onSortOptionChange = viewModel::onSortOptionChange,
                onApplyClick = { navController.popBackStack() },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(AppDestinations.FAVORITES_ROUTE) {
            val viewModel: FavoritesViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.errorFlow.collectLatest { error ->
                    snackbarHostState.showSnackbar(message = error, actionLabel = "Dismiss")
                }
            }

            FavoritesScreen(uiState = uiState, onFavoriteClick = viewModel::toggleFavorite)
        }
    }
}
