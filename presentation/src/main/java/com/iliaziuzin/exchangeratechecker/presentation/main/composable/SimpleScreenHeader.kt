package com.iliaziuzin.exchangeratechecker.presentation.main.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iliaziuzin.exchangeratechecker.ui.theme.BackgroundHeader
import com.iliaziuzin.exchangeratechecker.ui.theme.Outline


@Composable
fun SimpleScreenHeader(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues.Zero, title:String)

{
    Column(
        modifier = modifier
    ) {
        ScreenTitleComposable(text = "Favorites")

        Spacer(modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.fillMaxWidth().size(1.dp).background(color = MaterialTheme.colorScheme.Outline))
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleScreenHeaderPreview() {
    SimpleScreenHeader(
        paddingValues = PaddingValues(16.dp),
        title = "Favorites"
    )
}