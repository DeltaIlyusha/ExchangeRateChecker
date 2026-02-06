package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iliaziuzin.exchangeratechecker.ui.theme.ExchangeRateCheckerTheme
import com.iliaziuzin.exchangeratechecker.ui.theme.TextDefault

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersScreen(
    currentSortOption: SortOption,
    onSortOptionChange: (SortOption) -> Unit,
    onApplyClick: () -> Unit,
    onBackClick: () -> Unit
) {
    ExchangeRateCheckerTheme {

    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Filters",
                        style = MaterialTheme.typography.titleLarge
                    )
                        },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.size(48.dp),
                        onClick = onBackClick
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Text(text = "SORT BY")
            SortOption.entries.forEach { sortOption ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = currentSortOption == sortOption,
                            onClick = { onSortOptionChange(sortOption) }
                        )
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = sortOption.displayName,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.TextDefault
                    )
                    RadioButton(
                        selected = currentSortOption == sortOption,
                        onClick = { onSortOptionChange(sortOption) }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onApplyClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Apply")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FiltersScreenPreview() {
    FiltersScreen(
        currentSortOption = SortOption.CODE_AZ,
        onSortOptionChange = {},
        onApplyClick = {},
        onBackClick = {}
    )
}
