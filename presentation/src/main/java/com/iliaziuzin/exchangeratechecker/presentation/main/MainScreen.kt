package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var tabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("Currencies", "Favorites")
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            Column {
                PrimaryTabRow (selectedTabIndex = tabIndex) {
                    tabs.forEachIndexed { index, title ->
                        Tab(text = { Text(title) },
                            selected = tabIndex == index,
                            onClick = { tabIndex = index })
                    }
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
                                onApplyClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
                1 -> FavoritesScreen(uiState = uiState, onRemoveFavorite = viewModel::removeFavorite)
            }
        }
    }
}
