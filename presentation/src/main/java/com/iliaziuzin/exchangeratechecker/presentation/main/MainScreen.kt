package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iliaziuzin.exchangeratechecker.presentation.R
import com.iliaziuzin.exchangeratechecker.ui.theme.BackgroundDefault
import com.iliaziuzin.exchangeratechecker.ui.theme.ExchangeRateCheckerTheme
import com.iliaziuzin.exchangeratechecker.ui.theme.LightPrimary
import com.iliaziuzin.exchangeratechecker.ui.theme.Outline
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val snackbarHostState = remember { SnackbarHostState() }

    val tabs = listOf(
        "Currencies" to AppDestinations.CURRENCIES_ROUTE,
        "Favorites" to AppDestinations.FAVORITES_ROUTE
    )

    val showBottomBar = tabs.any { it.second == currentRoute }

    LaunchedEffect(Unit) {
        viewModel.errorFlow.collectLatest { error ->
            snackbarHostState.showSnackbar(message = error, actionLabel = "Dismiss")
        }
    }

    ExchangeRateCheckerTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                if (showBottomBar) {
                    NavigationBar {
                        Column(Modifier.background(color = MaterialTheme.colorScheme.BackgroundDefault)) {
                            Spacer(modifier = Modifier
                                .fillMaxWidth()
                                .size(1.dp)
                                .background(color = MaterialTheme.colorScheme.Outline))
                            Row() {
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
                                        label = {
                                            Text(
                                                text = title,
                                                style = MaterialTheme.typography.labelSmall,
                                            )
                                        },
                                        icon = {
                                            val icon = when (index) {
                                                0 -> if (selected) R.drawable.icon_currencies_on else R.drawable.icon_currencies_off
                                                1 -> if (selected) R.drawable.icon_favorites_on else R.drawable.icon_favorites_off
                                                else -> R.drawable.icon_favorites_off
                                            }
                                            Icon(painterResource(id = icon), contentDescription = title)
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            indicatorColor = MaterialTheme.colorScheme.LightPrimary,
                                            selectedIconColor = MaterialTheme.colorScheme.primary,
                                            unselectedIconColor = MaterialTheme.colorScheme.secondary,
                                            unselectedTextColor = MaterialTheme.colorScheme.secondary
                                        )
                                    )
                                }
                            }
                        }

                    }
                }
            }
        ) { innerPadding ->
           AppNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
        }
    }
}
