package com.iliaziuzin.exchangeratechecker.presentation.main.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iliaziuzin.exchangeratechecker.R

@Composable
fun CurrencyComposable(
    modifier: Modifier = Modifier,
    code: String,
    rate: String = "",
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit = {}
) {
    Row(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        Text(text = code)
        Text(text = rate)
        IconButton(onClick = onFavoriteClick) {
            Icon(
                painter = painterResource(id = if (isFavorite) R.drawable.icon_favorites_on else R.drawable.icon_favorites_off),
                contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyComposablePreview() {
    CurrencyComposable(code = "USD", rate = "1.0", isFavorite = true, onFavoriteClick = {})
}
