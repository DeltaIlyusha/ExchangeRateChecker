package com.iliaziuzin.exchangeratechecker.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FiltersScreen(
    currentSortOption: SortOption,
    onSortOptionChange: (SortOption) -> Unit,
    onApplyClick: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Sort by")
        SortOption.values().forEach { sortOption ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentSortOption == sortOption,
                    onClick = { onSortOptionChange(sortOption) }
                )
                Text(text = sortOption.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() })
            }
        }
        Button(
            onClick = onApplyClick,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Apply")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FiltersScreenPreview() {
    FiltersScreen(
        currentSortOption = SortOption.NAME_ASC,
        onSortOptionChange = {},
        onApplyClick = {}
    )
}
