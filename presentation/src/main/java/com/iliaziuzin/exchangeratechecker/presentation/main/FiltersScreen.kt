package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iliaziuzin.exchangeratechecker.presentation.R
import com.iliaziuzin.exchangeratechecker.ui.theme.BackgroundHeader
import com.iliaziuzin.exchangeratechecker.ui.theme.OnPrimary
import com.iliaziuzin.exchangeratechecker.ui.theme.Primary
import com.iliaziuzin.exchangeratechecker.ui.theme.Secondary
import com.iliaziuzin.exchangeratechecker.ui.theme.TextDefault
import com.iliaziuzin.exchangeratechecker.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersScreen(
    modifier: Modifier = Modifier,
    currentSortOption: SortOption,
    onSortOptionChange: (SortOption) -> Unit,
    onApplyClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.BackgroundHeader),
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
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.Primary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Text(
                text = "SORT BY",
                color = MaterialTheme.colorScheme.TextSecondary,
                style = MaterialTheme.typography.titleSmall,
            )

            Spacer(modifier = Modifier.fillMaxWidth().size(12.dp))

            SortOption.entries.forEach { sortOption ->
                filterComposable(
                    currentSortOption = currentSortOption,
                    sortOption = sortOption,
                    onSortOptionChange = onSortOptionChange
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onApplyClick,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.Primary,
                    contentColor = MaterialTheme.colorScheme.OnPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.Primary,
                    disabledContentColor = MaterialTheme.colorScheme.OnPrimary
                )

            ) {
                Text(text= "Apply")
            }
        }
    }
}

@Composable
fun filterComposable(
    modifier: Modifier = Modifier,
    currentSortOption: SortOption,
    sortOption: SortOption,
    onSortOptionChange: (SortOption) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .selectable(
                selected = currentSortOption == sortOption,
                onClick = { onSortOptionChange(sortOption) }
            )
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = sortOption.displayName,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.TextDefault
        )

        val iconRes = if (sortOption == currentSortOption) R.drawable.icon_radio_on else R.drawable.icon_radio_off
        val tintColor = if (sortOption == currentSortOption) MaterialTheme.colorScheme.Primary else MaterialTheme.colorScheme.Secondary

        IconButton (
            modifier = Modifier.size(20.dp),
            onClick = { onSortOptionChange(sortOption) }
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = iconRes),
                tint = tintColor,
                contentDescription = "Select filter"
            )
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
